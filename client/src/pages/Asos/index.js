import React, { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import { FiArrowLeft, FiPlus, FiEdit } from "react-icons/fi";
import api from "../../services/api";
import './styles.css';
import logo from '../../assets/logo.png';

export default function Asos() {
    const [asos, setAsos] = useState([]);
    const [funcionarioNome, setFuncionarioNome] = useState('');
    const { funcionarioId } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        async function loadDados() {
            try {
                // Busca os dados do funcionário para exibir o nome dele no topo
                const funcionarioRes = await api.get(`/api/funcionario/v1/${funcionarioId}`);
                setFuncionarioNome(funcionarioRes.data.nome);
            } catch (error) {
                console.error("Erro ao carregar dados do funcionário", error);
                setFuncionarioNome("Funcionário não encontrado");
            }

            try {
                // Busca a lista de ASOs do funcionário
                const asosRes = await api.get(`/api/aso/v1/findAsoByFuncionarioId/${funcionarioId}`);
                
                // CORREÇÃO: Se o _embedded não existir, significa que não há registros,
                // então salvamos um array vazio [] de forma segura.
                const data = asosRes.data?._embedded?.asos || [];
                
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
                <Link className="button-voltar-list" to="/funcionarios">
                    <FiArrowLeft size={16}/> Voltar para Funcionários
                </Link>
            </header>

            <div className="list-header-aso">
                <div>
                    <h1>Histórico de Atestados de Saúde Ocupacional (ASO)</h1>
                    <h2>Funcionário: <strong>{funcionarioNome}</strong></h2>
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
                            <th>Tipo de Exame</th>
                            <th>Data do Exame</th>
                            <th>Resultado</th>
                            <th>Médico Examinador</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {asos.map(aso => (
                            <tr key={aso.id}>
                                <td>{aso.tipoExame}</td>
                                <td>{aso.dataExame}</td>
                                <td>
                                    <span className={`aso-status ${aso.resultado === 'APTO' ? 'apto' : 'inapto'}`}>
                                        {aso.resultado}
                                    </span>
                                </td>
                                <td>{aso.nomeMedico}</td>
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