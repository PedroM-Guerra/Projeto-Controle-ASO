import React, {useState, useEffect} from "react";
import { useNavigate, Link } from "react-router-dom";
import { FiEdit, FiSearch, FiChevronLeft, FiChevronRight, FiCheckCircle, FiXCircle, FiClipboard } from "react-icons/fi";

import api from "../../services/api";

import './styles.css';
import logo from '../../assets/logo.png'

export default function Funcionario(){

    // Inicializa os estados lendo direto do localStorage se houver histórico
    const [page, setPage] = useState(() => {
        return Number(localStorage.getItem('func_page')) || 0;
    });
    const [searchTerm, setSearchTerm] = useState(() => {
        return localStorage.getItem('func_search') || '';
    });
    const [funcionarios, setFuncionarios] = useState([]);
    const [totalPages, setTotalPages] = useState(0);

    const navigate = useNavigate();

    async function editFuncionario(id) {
        try {
            navigate(`/funcionario/new/${id}`)
        } catch (error) {
            alert("Edição de Funcionário falhou, tente novamente.")
        }
    }

    async function gerenciarAsos(id) {
        try {
            navigate(`/funcionario/${id}/asos`)
        } catch (error) {
            alert("Acesso ao gerenciamento de ASOs falhou, tente novamente.")
        }
    }

    async function fetchFuncionarios(searchPage = 0) {
        try {
            let response;
            
            if (searchTerm.trim() !== '') {
                response = await api.get(`/api/funcionario/v1/findFuncionarioByName/${searchTerm}`, {
                    params: {
                        page: searchPage,
                        limit: 4,
                        direction: 'asc'
                    }
                });
            } else {
                response = await api.get('/api/funcionario/v1', {
                    params: {
                        page: searchPage,
                        limit: 4,
                        direction: 'asc'
                    }
                });
            }

            // Captura os funcionários retornados
            const data = response.data._embedded?.funcionarios || [];
            
            // ATENÇÃO: Captura as informações de paginação que o Spring Boot envia no JSON
            const pageInfo = response.data.page || { totalPages: 0 };

            setFuncionarios(data);
            setPage(searchPage);
            setTotalPages(pageInfo.totalPages);

            // SALVA O ESTADO ATUAL NO LOCALSTORAGE
            localStorage.setItem('func_page', searchPage);
            localStorage.setItem('func_search', searchTerm);
        } catch (error) {
            alert("Erro ao buscar funcionários.");
        }
    }

    function handleSearch(e) {
        e.preventDefault();
        fetchFuncionarios(0); // Reseta para a primeira página na busca
    }

    useEffect(() => {
        // Pega os valores direto do estado inicial que definimos acima
        const paginaSalva = Number(localStorage.getItem('func_page')) || 0;
        fetchFuncionarios(paginaSalva);
    }, []);

    // Função auxiliar para renderizar os botões numéricos dinamicamente
    function renderPaginationButtons() {
    const buttons = [];
    const maxVisibleButtons = 2; // Quantidade de páginas vizinhas que aparecem ao redor da atual

    // Sempre adiciona a primeira página
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

    // Coloca reticências se a página atual estiver muito longe do começo
    if (page > maxVisibleButtons + 1) {
        buttons.push(<span key="ellipsis-start" className="pagination-ellipsis">...</span>);
    }

    // Calcula os limites de páginas que vão aparecer ao redor da página atual
    let startPage = Math.max(1, page - maxVisibleButtons);
    let endPage = Math.min(totalPages - 2, page + maxVisibleButtons);

    // Renderiza as páginas intermediárias vizinhas
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

    // Coloca reticências se a página atual estiver muito longe do fim
    if (page < totalPages - maxVisibleButtons - 2) {
        buttons.push(<span key="ellipsis-end" className="pagination-ellipsis">...</span>);
    }

    // Sempre adiciona a última página (se houver mais de uma página no total)
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
                        {/* O CARD INTEIRO AGORA É UM LINK PARA OS ASOS */}
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

            {/* Nova barra de paginação numerada */}
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