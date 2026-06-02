import React, { useState, useEffect, useCallback } from "react"; // Importe o useCallback
import { Link } from "react-router-dom";
import { FiSearch, FiChevronLeft, FiChevronRight } from "react-icons/fi";

import api from "../../services/api";

import './styles.css';
import logo from '../../assets/logo.png'

export default function Funcionario(){

    const [page, setPage] = useState(() => {
        return Number(localStorage.getItem('func_page')) || 0;
    });
    const [searchTerm, setSearchTerm] = useState(() => {
        return localStorage.getItem('func_search') || '';
    });
    const [funcionarios, setFuncionarios] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [filtroStatus, setFiltroStatus] = useState(() => {
        return localStorage.getItem('func_filtro_status') || 'todos';
    });

    const fetchFuncionarios = useCallback(async (searchPage = 0, statusAtual = 'todos', currentSearch = '') => {
        try {
            let response;
            
            if (currentSearch.trim() !== '') {
                response = await api.get(`/api/funcionario/v1/findFuncionarioByName/${currentSearch}`, {
                    params: { page: searchPage, limit: 4, direction: 'asc' }
                });
            } else if (statusAtual === 'vencidos') {
                response = await api.get('/api/funcionario/v1/comAsoVencido', {
                    params: { page: searchPage, limit: 4, direction: 'asc' }
                });
            } else if (statusAtual === 'alerta') {
                // Nova rota integrada
                response = await api.get('/api/funcionario/v1/comAsoVencendo', {
                    params: { page: searchPage, limit: 4, direction: 'asc' }
                });
            } else {
                response = await api.get('/api/funcionario/v1', {
                    params: { page: searchPage, limit: 4, direction: 'asc' }
                });
            }

            const data = response.data._embedded?.funcionarios || [];
            const pageInfo = response.data.page || { totalPages: 0 };

            setFuncionarios(data);
            setPage(searchPage);
            setTotalPages(pageInfo.totalPages);

            localStorage.setItem('func_page', searchPage);
            localStorage.setItem('func_search', currentSearch);
            localStorage.setItem('func_filtro_status', statusAtual);
        } catch (error) {
            alert("Erro ao buscar funcionários.");
        }
    }, []);

    function handleSearch(e) {
        e.preventDefault();
        fetchFuncionarios(0); 
    }

    useEffect(() => {
        const filtroSalvo = localStorage.getItem('func_filtro_status') || 'todos';
        const buscaSalva = localStorage.getItem('func_search') || '';
        
        fetchFuncionarios(page, filtroSalvo, buscaSalva);
    }, [fetchFuncionarios, page]);

    function renderPaginationButtons() {
        const buttons = [];
        const maxVisibleButtons = 2;

        buttons.push(
            <button 
                key={0} 
                className={`page-number ${page === 0 ? 'active' : ''}`}
                onClick={() => fetchFuncionarios(0)}
                type="button"
            >
                1
            </button>
        );

        if (page > maxVisibleButtons + 1) {
            buttons.push(<span key="ellipsis-start" className="pagination-ellipsis">...</span>);
        }

        let startPage = Math.max(1, page - maxVisibleButtons);
        let endPage = Math.min(totalPages - 2, page + maxVisibleButtons);

        for (let i = startPage; i <= endPage; i++) {
            buttons.push(
                <button 
                    key={i} 
                    className={`page-number ${page === i ? 'active' : ''}`}
                    onClick={() => fetchFuncionarios(i)}
                    type="button"
                >
                    {i + 1}
                </button>
            );
        }

        if (page < totalPages - maxVisibleButtons - 2) {
            buttons.push(<span key="ellipsis-end" className="pagination-ellipsis">...</span>);
        }

        if (totalPages > 1) {
            buttons.push(
                <button 
                    key={totalPages - 1} 
                    className={`page-number ${page === totalPages - 1 ? 'active' : ''}`}
                    onClick={() => fetchFuncionarios(totalPages - 1)}
                    type="button"
                >
                    {totalPages}
                </button>
            );
        }

        return buttons;
    }

    return (
        <div className="funcionario-container">
            <header>
                <img src={logo} alt="SGAMO"/>
                <span><strong>SGAMO</strong></span>
                <Link className="button" to="/funcionario/new/0">Adicionar Novo Funcionário</Link>
            </header>

            <div className="list-header">
                <h1>Listagem de Funcionários</h1>
                
                <div className="filter-container">
                    <label className="select-label">
                        
                        <select 
                            value={filtroStatus} 
                            onChange={e => {
                                const novoStatus = e.target.value;
                                setFiltroStatus(novoStatus);
                                fetchFuncionarios(0, novoStatus, searchTerm);
                            }}
                            disabled={searchTerm.trim() !== ''}
                        >
                            <option value="todos">Todos os funcionários</option>
                            <option value="vencidos">Apenas com ASO vencido</option>
                            <option value="alerta">Com ASO perto de vencer (30 dias)</option>
                        </select>
                    </label>
                </div>
    
                    <form onSubmit={handleSearch} className="search-form">
                        <input 
                            type="text" 
                            placeholder="Pesquisar por nome..." 
                            value={searchTerm}
                            onChange={e => setSearchTerm(e.target.value)}
                        />
                        <button type="submit">
                            <FiSearch size={16} />
                        </button>
                    </form>
                </div>
            <ul>
                {funcionarios.map(funcionario => (
                    <li key={funcionario.id} className="funcionario-item">
                        <Link to={`/funcionario/${funcionario.id}/asos`} className="funcionario-link">
                            <div className="funcionario-header">
                                <strong>{funcionario.nome}</strong>
                                {!funcionario.dataDemissao ? (
                                    <span className="status-badge ativo">Contrato Ativo</span>
                                ) : (
                                    <span className="status-badge inativo">Demitido</span>
                                )}
                            </div>
                            <div className="funcionario-dados-linha">
                                <p><strong>CPF:</strong> {funcionario.cpf}</p>
                                <p><strong>Matrícula:</strong> {funcionario.matricula}</p>
                            </div>
                        </Link>
                    </li>
                ))}
            </ul>

            {totalPages > 1 && (
                <div className="pagination-container">
                    <button 
                        disabled={page === 0} 
                        onClick={() => fetchFuncionarios(page - 1)}
                        className="pagination-arrow"
                        type="button"
                    >
                        <FiChevronLeft size={20} />
                    </button>

                    {renderPaginationButtons()}

                    <button 
                        disabled={page === totalPages - 1} 
                        onClick={() => fetchFuncionarios(page + 1)}
                        className="pagination-arrow"
                        type="button"
                    >
                        <FiChevronRight size={20} />
                    </button>
                </div>
            )}
        </div>
    );
}