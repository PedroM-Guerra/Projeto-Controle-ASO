import React, { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import { FiArrowLeft, FiPlus, FiEdit } from "react-icons/fi";
import api from "../../services/api";
import './styles.css';

export default function Asos() {
    const [asos, setAsos] = useState([]);

    const [nome, setNome] = useState('');
    const [cpf, setCpf] = useState('');
    const [matricula, setMatricula] = useState('');
    const [dataNascimento, setDataNascimento] = useState('');
    const [genero, setGenero] = useState('');
    const [setor, setSetor] = useState('');
    const [cargo, setCargo] = useState('');
    const [dataAdmissao, setDataAdmissao] = useState('');
    const [dataDemissao, setDataDemissao] = useState('');

    const [generos, setGeneros] = useState([]);
    const [setores, setSetores] = useState([]);
    const [cargos, setCargos] = useState([]);

    const [tiposAso, setTiposAso] = useState([]);
    const [resultadosAso, setResultadosAso] = useState([]);


    const { funcionarioId } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        api.get('api/aso/v1/tipos').then(response => {
            setTiposAso(Array.isArray(response.data) ? response.data : []);
        }).catch(err => console.error("Erro ao carregar tipos de ASO", err));

        api.get('api/aso/v1/resultados').then(response => {
            setResultadosAso(Array.isArray(response.data) ? response.data : []);
        }).catch(err => console.error("Erro ao carregar resultados de ASO", err));

        api.get('api/funcionario/v1/generos').then(response => {
            setGeneros(response.data);
        }).catch(err => console.error("Erro ao carregar gêneros", err));

        api.get('api/funcionario/v1/setores').then(response => {
            setSetores(response.data);
        }).catch(err => console.error("Erro ao carregar setores", err));

        api.get('api/funcionario/v1/cargos').then(response => {
            setCargos(response.data);
        }).catch(err => console.error("Erro ao carregar cargos", err));

    }, []);

    function formatarData(dataString) {
        if (!dataString) return "";
        
        const [ano, mes, dia] = dataString.split('-');
        return `${dia}/${mes}/${ano}`;
    }

    useEffect(() => {
        async function loadDados() {
            try {
                const response = await api.get(`/api/funcionario/v1/${funcionarioId}`);

                setNome(response.data.nome);
                setCpf(response.data.cpf);
                setMatricula(response.data.matricula);
                setDataNascimento(response.data.dataNascimento);
                setGenero(response.data.genero);            
                setCargo(response.data.cargo); 
                setSetor(response.data.setor);
                setDataAdmissao(response.data.dataAdmissao);
                setDataDemissao(response.data.dataDemissao);

            } catch (error) {
                console.error("Erro ao carregar dados do funcionário", error);
                setNome("Funcionário não encontrado");
            }

            try {
                const asosRes = await api.get(`/api/aso/v1/findAsoByFuncionarioId/${funcionarioId}`);

                const data = asosRes.data?._embedded?.asos || [];
    
                // Ordena diretamente garantindo que quem não tem data fique no final
                data.sort((a, b) => (b.dataEmissao || "").localeCompare(a.dataEmissao || ""));
                
                setAsos(data);
            } catch (error) {
                console.error("Erro ao carregar dados de ASO", error);
                setAsos([]);
            }
        }
        loadDados();
    }, [funcionarioId]);

    return (
        <div className="aso-container">
            <header>
                <Link className="button-voltar" to="/funcionarios">
                    <FiArrowLeft size={16}/> 
                    Voltar
                </Link>
            </header>

            <div className="list-header-aso">
                <div>
                    <h1>Histórico de Atestados de Saúde Ocupacional (ASO)</h1>
                    <h2>Funcionário: <strong>{nome}</strong></h2>
                    <h2>CPF: <strong>{cpf}</strong></h2>
                    <h2>Matrícula: <strong>{matricula}</strong></h2>
                    <h2>Gênero: <strong>{generos.find(g => g.codigo === genero)?.descricao || genero}</strong></h2>
                    <h2>Data de Nascimento: <strong>{formatarData(dataNascimento)}</strong></h2>
                    <h2>Setor: <strong>{setores.find(s => s.codigo === setor)?.descricao || setor}</strong></h2>
                    <h2>Cargo: <strong>{cargos.find(c => c.codigo === cargo)?.descricao || cargo}</strong></h2>
                    <h2>Data de Admissão: <strong>{formatarData(dataAdmissao)}</strong></h2>
                    {dataDemissao && (
                        <h2>Data de Demissão: <strong>{formatarData(dataDemissao)}</strong></h2>
                    )}
                    
                    {/* Botão de Editar Funcionário posicionado logo abaixo dos dados dele */}
                <button 
                    className="btn-editar-funcionario" 
                    onClick={() => navigate(`/funcionario/new/${funcionarioId}`)}
                    type="button"
                >
                    <FiEdit size={16}/>
                    Editar Dados do Funcionário
                </button>
                </div>
                
                <Link className="button-add-aso" to={`/funcionario/${funcionarioId}/aso/new/0`}>
                    <FiPlus size={16} /> Cadastrar Novo ASO
                </Link>
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