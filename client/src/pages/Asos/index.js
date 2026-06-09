import React, { useState, useEffect, useCallback } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import { FiArrowLeft, FiPlus, FiEdit } from "react-icons/fi";
import api from "../../services/api";
import './styles.css';

export default function Asos() {
    const { funcionarioId } = useParams();
    const navigate = useNavigate();

    // Estados de dados principais
    const [funcionario, setFuncionario] = useState({});
    const [asos, setAsos] = useState([]);

    // Estados de mapeamento (Tabelas auxiliares)
    const [generos, setGeneros] = useState([]);
    const [setores, setSetores] = useState([]);
    const [cargos, setCargos] = useState([]);
    const [tiposAso, setTiposAso] = useState([]);
    const [resultadosAso, setResultadosAso] = useState([]);

    // Formatação de data padrão universal
    const formatarData = (dataString) => {
        if (!dataString) return "";
        const [ano, mes, dia] = dataString.split('-');
        return `${dia}/${mes}/${ano}`;
    };

    // Carrega tabelas de parametrização apenas uma vez ao montar
    useEffect(() => {
        const carregarParametros = async () => {
            try {
                const [tipos, resultados, gens, sets, crgs] = await Promise.all([
                    api.get('api/aso/v1/tipos'),
                    api.get('api/aso/v1/resultados'),
                    api.get('api/funcionario/v1/generos'),
                    api.get('api/funcionario/v1/setores'),
                    api.get('api/funcionario/v1/cargos')
                ]);

                setTiposAso(Array.isArray(tipos.data) ? tipos.data : []);
                setResultadosAso(Array.isArray(resultados.data) ? resultados.data : []);
                setGeneros(gens.data || []);
                setSetores(sets.data || []);
                setCargos(crgs.data || []);
            } catch (err) {
                console.error("Erro ao carregar tabelas auxiliares", err);
            }
        };

        carregarParametros();
    }, []);

    // Carrega dados dinâmicos do funcionário e histórico de ASOs
    const loadDadosIniciais = useCallback(async () => {
        try {
            const funcRes = await api.get(`/api/funcionario/v1/${funcionarioId}`);
            setFuncionario(funcRes.data);
        } catch (error) {
            console.error("Erro ao carregar dados do funcionário", error);
            setFuncionario({ nome: "Funcionário não encontrado" });
        }

        try {
            const asosRes = await api.get(`/api/aso/v1/findAsoByFuncionarioId/${funcionarioId}`);
            const data = asosRes.data?._embedded?.asos || [];
            
            // Ordenação decrescente por data de emissão
            data.sort((a, b) => (b.dataEmissao || "").localeCompare(a.dataEmissao || ""));
            setAsos(data);
        } catch (error) {
            console.error("Erro ao carregar dados de ASO", error);
            setAsos([]);
        }
    }, [funcionarioId]);

    useEffect(() => {
        loadDadosIniciais();
    }, [loadDadosIniciais]);

    return (
        <div className="aso-container">
            <header className="aso-header">
                <Link className="button-voltar" to="/funcionarios">
                    <FiArrowLeft size={16}/> Voltar
                </Link>              
                <Link className="button-add-aso" to={`/funcionario/${funcionarioId}/aso/new/0`}>
                    <FiPlus size={16} /> Cadastrar Novo ASO
                </Link>
            </header>

            <div className="list-header-aso">
                <div className="employee-profile-card">
                    <div className="profile-top-bar">
                        <div>
                            <h1>Histórico de Atestados de Saúde Ocupacional (ASO)</h1>
                            <p className="subtitle">Consulte e gerencie os registros médicos do colaborador</p>
                        </div>
                        <button 
                            className="btn-editar-funcionario" 
                            onClick={() => navigate(`/funcionario/new/${funcionarioId}`)}
                            type="button"
                        >
                            <FiEdit size={16}/> Editar Funcionário
                        </button>
                    </div>

                    <div className="profile-data-grid">
                        <div className="data-item">
                            <span className="data-label">Funcionário</span>
                            <span className="data-value">{funcionario.nome || ''}</span>
                        </div>
                        <div className="data-item">
                            <span className="data-label">CPF</span>
                            <span className="data-value">{funcionario.cpf || ''}</span>
                        </div>
                        <div className="data-item">
                            <span className="data-label">Matrícula</span>
                            <span className="data-value">{funcionario.matricula || ''}</span>
                        </div>
                        <div className="data-item">
                            <span className="data-label">Gênero</span>
                            <span className="data-value">
                                {generos.find(g => g.codigo === funcionario.genero)?.descricao || funcionario.genero}
                            </span>
                        </div>
                        <div className="data-item">
                            <span className="data-label">Data de Nascimento</span>
                            <span className="data-value">{formatarData(funcionario.dataNascimento)}</span>
                        </div>
                        <div className="data-item">
                            <span className="data-label">Setor</span>
                            <span className="data-value">
                                {setores.find(s => s.codigo === funcionario.setor)?.descricao || funcionario.setor}
                            </span>
                        </div>
                        <div className="data-item">
                            <span className="data-label">Cargo</span>
                            <span className="data-value">
                                {cargos.find(c => c.codigo === funcionario.cargo)?.descricao || funcionario.cargo}
                            </span>
                        </div>
                        <div className="data-item">
                            <span className="data-label">Data de Admissão</span>
                            <span className="data-value">{formatarData(funcionario.dataAdmissao)}</span>
                        </div>
                        {funcionario.dataDemissao && (
                            <div className="data-item status-demitido">
                                <span className="data-label">Data de Demissão</span>
                                <span className="data-value">{formatarData(funcionario.dataDemissao)}</span>
                            </div>
                        )}
                    </div>
                </div>
            </div>

            {asos.length === 0 ? (
                <p className="empty-message">Nenhum ASO cadastrado para este funcionário.</p>
            ) : (
                <table className="aso-table">
                    <thead>
                        <tr>
                            <th>Tipo de ASO</th>
                            <th>Resultado</th>
                            <th>Médico</th>
                            <th>CRM</th>
                            <th>Emissão</th>
                            <th>Validade</th>
                            <th>Exames</th>
                            <th>Documento</th>
                            <th>Editar</th>
                        </tr>
                    </thead>
                    <tbody>
                        {asos.map(aso => (
                            <tr key={aso.id}>
                                <td>
                                    {tiposAso.find(t => t.codigo === aso.tipoAso)?.descricao || aso.tipoAso}
                                </td>
                                <td>
                                    <span className={`aso-status ${aso.resultadoAso === 'A' ? 'apto' : 'inapto'}`}>
                                        {resultadosAso.find(r => r.codigo === aso.resultadoAso)?.descricao || aso.resultadoAso}
                                    </span>
                                </td>
                                <td>{aso.nomeMedico}</td>
                                <td>{aso.crmMedico}</td>
                                <td>{formatarData(aso.dataEmissao)}</td>
                                <td>{formatarData(aso.dataValidade)}</td>
                                <td>{aso.descricaoExame}</td>
                                <td>
                                    {aso.urlDocumentoScan ? (
                                        <a 
                                            href={`http://localhost:8080/api/file/v1/downloadFile/${aso.urlDocumentoScan}`} 
                                            target="_blank" 
                                            rel="noopener noreferrer"
                                            className="btn-visualizar"
                                        >
                                            Visualizar
                                        </a>
                                    ) : (
                                        <span className="sem-documento">Sem arquivo</span>
                                    )}
                                </td>
                                <td>
                                    <button onClick={() => navigate(`/funcionario/${funcionarioId}/aso/new/${aso.id}`)} type="button">
                                        <FiEdit size={18} color="#251fc5"/>
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}