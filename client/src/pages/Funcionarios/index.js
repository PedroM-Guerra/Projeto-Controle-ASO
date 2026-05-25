import React, {useState, useEffect} from "react";
import { useNavigate, Link } from "react-router-dom";
import { FiPower, FiEdit, FiSearch, FiChevronLeft, FiChevronRight, FiCheckCircle, FiXCircle, FiClipboard } from "react-icons/fi";

import api from "../../services/api";

import './styles.css';
import logo from '../../assets/logo.png'

export default function Funcionario(){

    const [funcionarios, setFuncionarios] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0); 
    const [searchTerm, setSearchTerm] = useState('');

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
        } catch (error) {
            alert("Erro ao buscar funcionários.");
        }
    }

    function handleSearch(e) {
        e.preventDefault();
        fetchFuncionarios(0); // Reseta para a primeira página na busca
    }

    useEffect(() => {
        fetchFuncionarios(0);
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
                <span>Bem Vindo, <strong>Usuário</strong>!</span>
                <Link className="button" to="/funcionario/new/0">Adicionar Novo Funcionário</Link>
                <button type="button">
                    <FiPower size={18} color="#251FC5"></FiPower>
                </button>
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
                    <li key={funcionario.id}>
                        <div className="funcionario-header">
                            <strong>{funcionario.nome}</strong>
                            {!funcionario.dataDemissao ? (
                                <span className="status-badge ativo">Contratado</span>
                            ) : (
                                <span className="status-badge inativo">Demitido</span>
                            )}
                        </div>

                        <p>{funcionario.cpf}</p>

                        {/* Container para organizar os botões de ação verticalmente ou lado a lado */}
                        <div className="actions-container" style={{ display: 'flex', flexDirection: 'column', gap: '12px', position: 'absolute', right: '24px', top: '24px' }}>
                            <button onClick={() => editFuncionario(funcionario.id)} type="button" title="Editar Funcionário">
                                <FiEdit size={20} color="#251fc5"/>
                            </button>

                            <button onClick={() => gerenciarAsos(funcionario.id)} type="button" title="Gerenciar ASOs">
                                <FiClipboard size={20} color="#251fc5"/>
                            </button>
                        </div>
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