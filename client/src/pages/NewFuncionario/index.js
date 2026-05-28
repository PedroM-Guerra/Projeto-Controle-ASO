import React, {useState, useEffect} from "react";
import { useNavigate, Link, useParams } from "react-router-dom";
import { FiArrowLeft } from "react-icons/fi";

import api from "../../services/api";

import './styles.css';

export default function NewFuncionario(){

    const [id, setId] = useState('');
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

    const {funcionarioId} = useParams();

    const navigate = useNavigate();

// Carrega todas as listas de Enums ao iniciar o componente
    useEffect(() => {
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

    useEffect(() => {
        async function loadFuncionario() {
            try {
                const response = await api.get(`api/funcionario/v1/${funcionarioId}`)

                setId(response.data.id);
                setNome(response.data.nome);
                setCpf(response.data.cpf);
                setMatricula(response.data.matricula);
                setDataNascimento(response.data.dataNascimento);
                setGenero(response.data.genero);            
                setSetor(response.data.setor);
                setCargo(response.data.cargo);
                setDataAdmissao(response.data.dataAdmissao);
                setDataDemissao(response.data.dataDemissao);

            } catch (error) {
                alert('Erro ao carregar Funcionário, tente novamente.');
                navigate(`/funcionarios`);
            }
        }

        if (funcionarioId === '0') {
            return;
        }else loadFuncionario();
    }, [funcionarioId, navigate])    

    async function handleSaveOrUpdateFuncionario(e) {
        e.preventDefault();

        const data = {
            nome,
            cpf: cpf ? cpf.replace(/\D/g, "") : null,
            matricula,
            dataNascimento,
            genero: genero === "" ? null : genero,
            setor: setor === "" ? null : setor,
            cargo: cargo === "" ? null : cargo,
            dataAdmissao,
            dataDemissao: dataDemissao === "" ? null : dataDemissao,
            enabled: true
        }

        //console.log("DADOS QUE ESTÃO INDO PARA O BACK-END:", data);

        try {
            if (funcionarioId === '0') {
                await api.post('api/funcionario/v1', data);
                alert('Funcionário cadastrado com sucesso!');
                navigate('/funcionarios');

            } else {
                data.id = id;
                await api.put('api/funcionario/v1', data);
                alert('Funcionário atualizado com sucesso!');
                navigate('/funcionarios')

            }

        } catch (err) {
            // 1. Verifica se o servidor enviou uma resposta de erro estruturada
            if (err.response && err.response.data) {
                const erroBack = err.response.data;

                // Se o erro possuir a propriedade 'message' (caso da duplicidade do CPF / status 409)
                if (erroBack.message) {
                    alert(`Erro ao salvar:\n\n${erroBack.message}`);
                } 
                // Se for um mapa de validação de campos (caso dos campos vazios ou inválidos / status 400)
                else {
                    // Transforma o objeto de erros em uma lista de mensagens separadas por linha
                    const mensagensDeAviso = Object.values(erroBack).join('\n');
                    alert(`Erro ao salvar, corrija os campos:\n\n${mensagensDeAviso}`);
                }
            } else {
                // 2. Tratamento para caso o servidor do Spring Boot esteja desligado ou inacessível
                alert("Não foi possível conectar ao servidor. Tente novamente mais tarde.");
            }
        }
    }

    async function handleDelete() {

        const mensagem = 
        "ATENÇÃO!\n\n" +
        "Você está prestes a apagar este funcionário do sistema.\n" +
        "Esta ação impedirá o acesso e registros ativos para este perfil.\n\n" +
        "Deseja continuar com a desativação?";

        const confirmacao = window.confirm(mensagem);
    
        if (!confirmacao) return;
        
            try {
                await api.patch(`api/funcionario/v1/${id}`);
                alert('Funcionário desativado com sucesso!');
                navigate('/funcionarios');
            } catch (err) {
                alert('Erro ao desativar funcionário, tente novamente.');
            }
    }

    function handleFormKeyDown(e) {
        if (e.key === 'Enter') {
            // Bloqueia o submit automático do formulário
            e.preventDefault();

            // Lista de elementos onde o usuário digita ou seleciona algo
            const elementosFocaveis = 'input, select, textarea, button[type="submit"]';
            
            // Pega todos os elementos interativos do formulário em formato de Array
            const lista = Array.from(e.currentTarget.querySelectorAll(elementosFocaveis));
            
            // Descobre onde o usuário está agora
            const indiceAtual = lista.indexOf(e.target);

            // Se houver um próximo campo na lista, pula para ele
            if (indiceAtual > -1 && indiceAtual < lista.length - 1) {
                lista[indiceAtual + 1].focus();
            }
        }
    }

    function formatarCPF(value) {
        return value
            .replace(/\D/g, "") // Remove tudo o que não for número
            .replace(/(\d{3})(\d)/, "$1.$2") // Coloca ponto após os 3 primeiros dígitos
            .replace(/(\d{3})(\d)/, "$1.$2") // Coloca ponto após os 6 primeiros dígitos
            .replace(/(\d{3})(\d{1,2})$/, "$1-$2") // Coloca hífen antes dos 2 últimos dígitos
            .slice(0, 14); // Limita o tamanho máximo do campo formatado
    }

    return(
        <div className="new-funcionario-container">
            <div className="content">
                <section className="form">
                    <div className="header-actions">

                        <Link 
                            className="button-voltar" 
                            to={funcionarioId === '0' ? "/funcionarios" : `/funcionario/${funcionarioId}/asos`}
                        >
                            <FiArrowLeft size={16}/>
                            Voltar
                        </Link>
                           
                        {funcionarioId !== '0' && (
                            <button 
                                className="button-delete-top" 
                                type="button" 
                                onClick={handleDelete}
                            >
                                Apagar Funcionário
                            </button>
                        )}
                    </div>

                    <div className="title-container">
                        <h1>{funcionarioId === '0' ? "Cadastrar" : "Atualizar Dados do "} Funcionário</h1>
                        
                        {/* Só exibe o estado se for uma edição e se os dados já tiverem carregado */}
                        {funcionarioId !== '0' && id && (
                            !dataDemissao ? (
                                <span className="status-badge-edit ativo">Contratado</span>
                            ) : (
                                <span className="status-badge-edit inativo">Demitido</span>
                            )
                        )}
                    </div>
                    
                    <p>Preencha as informações do funcionário{funcionarioId === '0' ? "." : `: # ${funcionarioId}`}</p>
                </section>

                <form onSubmit={handleSaveOrUpdateFuncionario} onKeyDown={handleFormKeyDown}>
                    <div className="form-grid">
                        <div className="input-group">
                            <label>Nome</label>
                            <input 
                            value={nome} 
                            onChange={e => setNome(e.target.value)} />
                        </div>
                        <div className="input-group">
                            <label>CPF</label>
                            <input 
                                type="text"
                                placeholder="000.000.000-00"
                                value={cpf || ""} 
                                onChange={e => setCpf(formatarCPF(e.target.value))} 
                            />
                        </div>
                    <div className="input-group">
                        <label>Matrícula</label>
                        <input
                            value={matricula}
                            onChange={e => setMatricula(e.target.value)}
                        />
                    </div>
                    <div className="input-group">
                    <label htmlFor="genero">Gênero</label>
                    <select 
                        id="genero" 
                        value={genero} 
                        onChange={e => setGenero(e.target.value)}
                    >
                        <option value="">Selecione o gênero</option>
                        {generos.map(g => (
                            <option key={g.codigo} value={g.codigo}>
                                {g.descricao}
                            </option>
                        ))}
                    </select>
                </div>
                    <div className="input-group">
                        <label>Data de Nascimento</label>
                        <input
                            type="date"
                            value={dataNascimento}
                            onChange={e => setDataNascimento(e.target.value)}
                        />
                    </div>
                    <div className="input-group">
                        <label>Data de Admissão</label>
                        <input
                            type="date"
                            value={dataAdmissao}
                            onChange={e => setDataAdmissao(e.target.value)}
                        />
                    </div>
                    <div className="input-group">
                        <label>Data de Demissão</label>
                        <input
                            type="date"
                            value={dataDemissao || ""}
                            onChange={e => setDataDemissao(e.target.value)}
                        />
                    </div>
                    <div className="input-group">
                            <label htmlFor="setor">Setor</label>
                            <select 
                                id="setor" 
                                value={setor} 
                                onChange={e => setSetor(e.target.value)}
                            >
                                <option value="">Selecione o setor</option>
                                {setores.map(s => (
                                    <option key={s.codigo} value={s.codigo}>
                                        {s.descricao}
                                    </option>
                                ))}
                            </select>
                        </div>
                    <div className="input-group">
                            <label htmlFor="cargo">Cargo</label>
                            <select 
                                id="cargo" 
                                value={cargo} 
                                onChange={e => setCargo(e.target.value)}
                            >
                                <option value="">Selecione o cargo</option>
                                {cargos.map(c => (
                                    <option key={c.codigo} value={c.codigo}>
                                        {c.descricao}
                                    </option>
                                ))}
                            </select>
                        </div>
                </div>

                <button className="button" type="submit">{funcionarioId === '0' ? 'Cadastrar' : 'Salvar'}</button>
                </form>
            </div>
        </div>
    );
}