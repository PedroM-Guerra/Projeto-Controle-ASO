import React, { useState, useEffect } from "react";
import { useNavigate, Link, useParams } from "react-router-dom";
import { FiArrowLeft } from "react-icons/fi";

import api from "../../services/api";

import './styles.css';

export default function NewAso() {
    const [id, setId] = useState(null);
    const [crmMedico, setCrmMedico] = useState('');
    const [dataEmissao, setDataEmissao] = useState('');
    const [dataValidade, setDataValidade] = useState('');
    const [descricaoExame, setDescricaoExame] = useState('');
    const [nomeMedico, setNomeMedico] = useState('');
    const [resultadoAso, setResultadoAso] = useState('');
    const [tipoAso, setTipoAso] = useState('');
    const [urlDocumentoScan, setUrlDocumentoScan] = useState(''); 

    const [tiposAso, setTiposAso] = useState([]);
    const [resultadosAso, setResultadosAso] = useState([]);

    const { asoId } = useParams(); 
    const { funcionarioId } = useParams(); 

    const navigate = useNavigate();

    // Carrega os Enums do banco para preencher os selects ao abrir a tela
    useEffect(() => {
        api.get('api/aso/v1/tipos').then(response => {
            setTiposAso(Array.isArray(response.data) ? response.data : []);
        }).catch(err => console.error("Erro ao carregar tipos de ASO", err));

        api.get('api/aso/v1/resultados').then(response => {
            setResultadosAso(Array.isArray(response.data) ? response.data : []);
        }).catch(err => console.error("Erro ao carregar resultados de ASO", err));
    }, []);

    async function loadAso() {
        try {
            const response = await api.get(`api/aso/v1/${asoId}`)

            setId(response.data.id);
            setCrmMedico(response.data.crmMedico);
            setDataEmissao(response.data.dataEmissao);
            setDataValidade(response.data.dataValidade);
            setDescricaoExame(response.data.descricaoExame);
            setNomeMedico(response.data.nomeMedico);
            setResultadoAso(response.data.resultadoAso);
            setTipoAso(response.data.tipoAso);
            setUrlDocumentoScan(response.data.urlDocumentoScan);

        } catch (error) {
            alert('Erro ao carregar ASO, tente novamente.');
            navigate(`/asos`);
        }
    }

    useEffect(() => {
        if (asoId === '0') {
            return;
        }else loadAso();
    }, [asoId])    

    async function handleSaveOrUpdateAso(e) {
        e.preventDefault();

        // Monta o objeto exatamente com as chaves que o Spring Boot espera receber
        const data = {
            funcionarioId,
            crmMedico,
            nomeMedico,
            descricaoExame,
            urlDocumentoScan,
            dataEmissao,
            dataValidade,
            tipoAso,
            resultadoAso,
            enabled: true
        };

        console.log("DADOS QUE ESTÃO INDO PARA O BACK-END:", data);

        try {
            if (asoId === '0') {
                await api.post('api/aso/v1', data);
                alert('ASO cadastrado com sucesso!');
                navigate(`/funcionario/${funcionarioId}/asos`);
            } else {
                data.id = id;
                await api.put('api/aso/v1', data);
                navigate(`/funcionario/${funcionarioId}/asos`)

            }
            
        } catch (err) {
            alert('Erro ao cadastrar o registro de ASO, tente novamente.');
        }
    }

    async function handleDelete() {

        const mensagem = 
        "ATENÇÃO!\n\n" +
        "Você está prestes a apagar este ASO do sistema.\n" +
        "Esta ação impedirá o acesso à este Atestado.\n\n" +
        "Deseja continuar com a desativação?";

        const confirmacao = window.confirm(mensagem);
    
        if (!confirmacao) return;
        
            try {
                await api.patch(`api/aso/v1/${asoId}`);
                alert('ASO desativado com sucesso!');
                navigate(`/funcionario/${funcionarioId}/asos`);
            } catch (err) {
                alert('Erro ao desativar ASO, tente novamente.');
            }
    }

    function handleFormKeyDown(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            const elementosFocaveis = 'input, select, textarea, button[type="submit"]';
            const lista = Array.from(e.currentTarget.querySelectorAll(elementosFocaveis));
            const indiceAtual = lista.indexOf(e.target);

            if (indiceAtual > -1 && indiceAtual < lista.length - 1) {
                lista[indiceAtual + 1].focus();
            }
        }
    }

    return (
        <div className="new-funcionario-container">
            <div className="content">
                <section className="form">
                    <div className="header-actions">
                        <Link className="button-voltar" to={`/funcionario/${funcionarioId}/asos`}>
                            <FiArrowLeft size={16}/>
                            Voltar
                        </Link>
                        {asoId !== '0' && (
                            <button 
                                className="button-delete-top" 
                                type="button" 
                                onClick={handleDelete}
                            >
                                Apagar Aso
                            </button>
                        )}                        
                    </div>

                    <div className="title-container">
                        <h1>{asoId === '0' ? "Cadastrar Novo" : "Atualizar Dados do "} Aso</h1>
                    </div>
                    
                    <p>Preencha as informações do Atestado de Saúde Ocupacional.</p>
                </section>

                <form onSubmit={handleSaveOrUpdateAso} onKeyDown={handleFormKeyDown}>
                    <div className="form-grid">
                        <div className="input-group">
                            <label>Nome do Médico</label>
                            <input 
                            value={nomeMedico} 
                            onChange={e => setNomeMedico(e.target.value)} 
                            />
                        </div>

                        <div className="input-group">
                            <label>CRM do Médico</label>
                            <input 
                            value={crmMedico} 
                            onChange={e => setCrmMedico(e.target.value)} 
                            />
                        </div>

                        <div className="input-group">
                            <label htmlFor="tipoAso">Tipo de ASO</label>
                            <select 
                                id="tipoAso" 
                                value={tipoAso} 
                                onChange={e => setTipoAso(e.target.value)}
                            >
                                <option value="">Selecione o tipo</option>
                                {tiposAso.map(t => (
                                    <option key={t.codigo} value={t.codigo}>
                                        {t.descricao}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="input-group">
                            <label htmlFor="resultadoAso">Resultado do ASO</label>
                            <select 
                                id="resultadoAso" 
                                value={resultadoAso} 
                                onChange={e => setResultadoAso(e.target.value)}
                            >
                                <option value="">Selecione o resultado</option>
                                {resultadosAso.map(r => (
                                    <option key={r.codigo} value={r.codigo}>
                                        {r.descricao}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="input-group">
                            <label>Data de Emissão</label>
                            <input type="date" value={dataEmissao} onChange={e => setDataEmissao(e.target.value)} />
                        </div>

                        <div className="input-group">
                            <label>Data de Validade</label>
                            <input type="date" value={dataValidade} onChange={e => setDataValidade(e.target.value)} />
                        </div>

                        <div className="input-group">
                            <label>Descrição dos Exames</label>
                            <input value={descricaoExame} onChange={e => setDescricaoExame(e.target.value)} />
                        </div>

                        <div className="input-group">
                            <label>URL do Documento (Upload em breve)</label>
                            <input value={urlDocumentoScan} onChange={e => setUrlDocumentoScan(e.target.value)} placeholder="Caminho do arquivo..." />
                        </div>
                    </div>

                    <button className="button" type="submit">
                    {asoId === '0' ? 'Cadastrar' : 'Salvar'}
                    </button>                    
                </form>
            </div>
        </div>
    );
}