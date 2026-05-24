import React, {useState, useEffect} from "react";
import { useNavigate, Link, useParams } from "react-router-dom";
import { FiArrowLeft } from "react-icons/fi";

import api from "../../services/api";

import './styles.css';

import logo from '../../assets/logo.png'

export default function NewFuncionario(){

    const [id, setId] = useState(null);
    const [nome, setNome] = useState('');
    const [cpf, setCpf] = useState('');
    const [matricula, setMatricula] = useState('');
    const [dataNascimento, setDataNascimento] = useState('');
    const [genero, setGenero] = useState('');
    const [setor, setSetor] = useState('');
    const [cargo, setCargo] = useState('');
    const [dataAdmissao, setDataAdmissao] = useState('');
    const [dataDemissao, setDataDemissao] = useState('');

    const {funcionarioId} = useParams();

    const navigate = useNavigate();

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

    useEffect(() => {
        if (funcionarioId === '0') {
            return;
        }else loadFuncionario();
    }, [funcionarioId])    

    async function saveOrUpdate(e) {
        e.preventDefault();

        const data = {
            nome,
            cpf,
            matricula,
            dataNascimento,
            genero,
            setor,
            cargo,
            dataAdmissao,
            dataDemissao,
            enabled: true
        }

        try {
            if (funcionarioId === '0') {
                await api.post('api/funcionario/v1', data);
                navigate('/funcionarios');

            } else {
                data.id = id;
                await api.post('api/funcionario/v1', data);
                navigate('/funcionarios')

            }

        } catch (err) {
            alert('Erro ao salvar Funcionário, tente novamente.')
        }
    }

    return(
        <div className="new-funcionario-container">
            <div className="content">
                <section className="form">
                    <Link className="back-link" to="/funcionarios">
                        <FiArrowLeft size={16} color="#251fc5"/>
                        Voltar
                    </Link>
                    
                    <h1>{funcionarioId === '0' ? "Cadastrar" : "Atualziar dados do Funcionário"} Funcionário</h1>
                    <p>Preencha as informações do funcionário. # {funcionarioId}</p>
                    
                </section>
                <form onSubmit={saveOrUpdate}>
                    <label htmlFor="nome">Nome</label>
                    <input
                        placeholder="Nome"
                        value={nome}
                        onChange={e => setNome(e.target.value)}
                    />
                    <input
                        placeholder="CPF"
                        value={cpf}
                        onChange={e => setCpf(e.target.value)}
                    />
                    <input
                        placeholder="Matrícula"
                        value={matricula}
                        onChange={e => setMatricula(e.target.value)}
                    />
                    <input
                        placeholder="Gênero Biológico"
                        value={genero}
                        onChange={e => setGenero(e.target.value)}
                    />
                    <input
                        type="date"
                        placeholder="Data de Nascimento"
                        value={dataNascimento}
                        onChange={e => setDataNascimento(e.target.value)}
                    />
                    <input
                        type="date"
                        placeholder="Data de Admissão"
                        value={dataAdmissao}
                        onChange={e => setDataAdmissao(e.target.value)}
                    />
                    <input
                        type="date"
                        placeholder="Data de Demissão"
                        value={dataDemissao}
                        onChange={e => setDataDemissao(e.target.value)}
                    />
                    <input
                        placeholder="Setor"
                        value={setor}
                        onChange={e => setSetor(e.target.value)}
                    />
                    <input
                        placeholder="Cargo"
                        value={cargo}
                        onChange={e => setCargo(e.target.value)}
                    />

                    <button className="button" type="submit">{funcionarioId === '0' ? 'Cadastrar' : 'Salvar'}</button>
                </form>
            </div>
        </div>
    );
}