import React, { useState, useEffect, useCallback } from "react";
import { Link } from "react-router-dom";
import { FiSearch, FiChevronLeft, FiChevronRight, FiCreditCard, FiAward, FiPlus } from "react-icons/fi";
import api from "../../services/api";
import './styles.css';
import logo from '../../assets/logo.png';

export default function Funcionario() {
    const [page, setPage] = useState(() => Number(localStorage.getItem('func_page')) || 0);
    const [searchTerm, setSearchTerm] = useState(() => localStorage.getItem('func_search') || '');
    const [filtroStatus, setFiltroStatus] = useState(() => localStorage.getItem('func_filtro_status') || 'todos');
    const [funcionarios, setFuncionarios] = useState([]);
    const [totalPages, setTotalPages] = useState(0);

    // Requisição unificada com gerenciamento de estado e armazenamento local
    const fetchFuncionarios = useCallback(async (searchPage = 0, statusAtual = 'todos', currentSearch = '') => {
        try {
            let response;
            const params = { page: searchPage, limit: 4, direction: 'asc' };
            
            if (currentSearch.trim() !== '') {
                response = await api.get(`/api/funcionario/v1/findByName/${currentSearch}`, { params });
            } else if (statusAtual === 'vencidos') {
                response = await api.get('/api/funcionario/v1/comAsoVencido', { params });
            } else if (statusAtual === 'alerta') {
                response = await api.get('/api/funcionario/v1/comAsoVencendo', { params });
            } else {
                response = await api.get('/api/funcionario/v1', { params });
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
        fetchFuncionarios(0, filtroStatus, searchTerm); 
    }

    // Carrega os dados sempre que a página sofrer alteração mantendo os filtros
    useEffect(() => {
        fetchFuncionarios(page, filtroStatus, searchTerm);
    }, [fetchFuncionarios, page, filtroStatus, searchTerm]);

    function renderPaginationButtons() {
        const buttons = [];
        const maxVisibleButtons = 2;

        buttons.push(
            <button 
                key={0} 
                className={`page-number ${page === 0 ? 'active' : ''}`}
                onClick={() => fetchFuncionarios(0, filtroStatus, searchTerm)}
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
                    onClick={() => fetchFuncionarios(i, filtroStatus, searchTerm)}
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
                    onClick={() => fetchFuncionarios(totalPages - 1, filtroStatus, searchTerm)}
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
            <header className="main-header">
                <div className="brand-logo">
                    <img src={logo} alt="SigASO"/>
                    <span><strong>SigASO</strong></span>
                </div>
                <Link className="button-add-employee" to="/funcionario/new/0">
                    <FiPlus size={20} /> Adicionar Novo Funcionário
                </Link>
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

            <ul className="employees-list">
                {funcionarios.map(funcionario => (
                    <li 
                        key={funcionario.id} 
                        className={`funcionario-item ${!funcionario.dataDemissao ? 'borda-ativo' : 'borda-inativo'}`}
                    >
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
                                <div className="dado-item">
                                    <div className="icon-wrapper">
                                        <FiCreditCard size={20} />
                                    </div>
                                    <div className="dado-texto">
                                        <strong>CPF</strong>
                                        <span>{funcionario.cpf}</span>
                                    </div>
                                </div>

                                <div className="dado-item">
                                    <div className="icon-wrapper">
                                        <FiAward size={20} />
                                    </div>
                                    <div className="dado-texto">
                                        <strong>Matrícula</strong>
                                        <span>{funcionario.matricula}</span>
                                    </div>
                                </div>
                            </div>
                        </Link>
                    </li>
                ))}
            </ul>

            {totalPages > 1 && (
                <div className="pagination-container">
                    <button 
                        disabled={page === 0} 
                        onClick={() => setPage(prev => prev - 1)}
                        className="pagination-arrow"
                        type="button"
                    >
                        <FiChevronLeft size={20} />
                    </button>

                    {renderPaginationButtons()}

                    <button 
                        disabled={page === totalPages - 1} 
                        onClick={() => setPage(prev => prev + 1)}
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