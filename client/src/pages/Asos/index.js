import React, { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import { FiArrowLeft, FiPlus, FiEdit } from "react-icons/fi";
import api from "../../services/api";
import './styles.css';
import logo from '../../assets/logo.png';

export default function Asos() {
    const [asos, setAsos] = useState([]);

    const [nome, setNome] = useState('');
    const [cpf, setCpf] = useState('');
    const [matricula, setMatricula] = useState('');
    const [dataNascimento, setDataNascimento] = useState('');
    const [genero, setGenero] = useState('');
    const [cargo, setCargo] = useState('');

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
    }, []);

    function formatarData(dataString) {
        if (!dataString) return "";
        
        // Divide "AAAA-MM-DD" e organiza em [ano, mes, dia]
        const [ano, mes, dia] = dataString.split('-');
        
        // Devolve no padrão brasileiro "DD/MM/AAAA"
        return `${dia}/${mes}/${ano}`;
    }

    useEffect(() => {
        async function loadDados() {
            try {
                // Busca os dados do funcionário para exibir o nome dele no topo
                const response = await api.get(`/api/funcionario/v1/${funcionarioId}`);
                setNome(response.data.nome);
                setCpf(response.data.cpf);
                setMatricula(response.data.matricula);
                setDataNascimento(response.data.dataNascimento);
                setGenero(response.data.genero);            
                setCargo(response.data.cargo); 

            } catch (error) {
                console.error("Erro ao carregar dados do funcionário", error);
                setNome("Funcionário não encontrado");
            }

            try {
                // Busca a lista de ASOs do funcionário
                const asosRes = await api.get(`/api/aso/v1/findAsoByFuncionarioId/${funcionarioId}`);
                
                // CORREÇÃO: Se o _embedded não existir, significa que não há registros,
                // então salvamos um array vazio [] de forma segura.
                const data = asosRes.data?._embedded?.asos || [];
                //console.log("Dados que vao pros aso:", data);
                setAsos(data);
            } catch (error) {
                console.error("Erro ao carregar dados de ASO", error);
                // Se o servidor responder com qualquer status de erro, também limpamos o estado
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
                    <h2>Gênero: <strong>{genero}</strong></h2>
                    <h2>Cargo: <strong>{cargo}</strong></h2>
                    <h2>Data de Nascimento: <strong>{formatarData(dataNascimento)}</strong></h2>
                </div>
                {/* Botão que navega para o formulário de novo ASO passando o ID do funcionário */}
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
                            <th>Tipo do ASO</th>
                            <th>Resultado</th>
                            <th>Médico Examinador</th>
                            <th>CRM do Médico</th>
                            <th>Data de Emissão</th>
                            <th>Data de Validade</th>
                            <th>Descrição</th>
                            <th>Ações</th>
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