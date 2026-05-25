import React, { useState, useEffect } from "react";
import { useNavigate, Link, useParams } from "react-router-dom";
import { FiArrowLeft } from "react-icons/fi";

import api from "../../services/api";

import './styles.css';

export default function NewAso() {
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

    const { funcionarioId } = useParams(); // Só precisamos do ID do funcionário para vincular o cadastro
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

    async function handleCadastrarAso(e) {
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
            resultadoAso
        };

        console.log("DADOS QUE ESTÃO INDO PARA O BACK-END:", data);

        try {
            await api.post('api/aso/v1', data);
            alert('ASO cadastrado com sucesso!');
            navigate(`/funcionario/${funcionarioId}/asos`);
        } catch (err) {
            alert('Erro ao cadastrar o registro de ASO, tente novamente.');
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
                    </div>

                    <div className="title-container">
                        <h1>Cadastrar Novo ASO</h1>
                    </div>
                    
                    <p>Preencha as informações do atestado médico ocupacional.</p>
                </section>

                <form onSubmit={handleCadastrarAso} onKeyDown={handleFormKeyDown}>
                    <div className="form-grid">
                        <div className="input-group">
                            <label>Nome do Médico</label>
                            <input value={nomeMedico} onChange={e => setNomeMedico(e.target.value)} />
                        </div>

                        <div className="input-group">
                            <label>CRM do Médico</label>
                            <input value={crmMedico} onChange={e => setCrmMedico(e.target.value)} />
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
                        Cadastrar
                    </button>
                </form>
            </div>
        </div>
    );
}