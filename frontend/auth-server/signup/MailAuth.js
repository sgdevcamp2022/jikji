import {useState, useEffect} from "react";
import {MailAuthUser} from "../action/mailAuthUser";
import axios from "axios";
import {useLocation, useHistory, Link} from "react-router-dom";

function MailAuth() {
    const location = useLocation();
    const history = useHistory();
    const [Email, setEmail] = useState("");
    const [Code, setCode] = useState("");
    const [Error, setError] = useState("");

    useEffect(() => {
        setEmail(location.state);
        const email = location.state.toString()
        console.log(email, typeof email)
        let body = {
            email: email
        }
        axios.post("/signup/sendMail", body)
            .then(response => console.log(response))
            .catch(error => console.log(error))
    },[])
    const onCodeHandler = (event) => {
        setCode(event.currentTarget.value);
    }

    const onSubmitHandler = (event) => {
        // 버튼만 누르면 리로드 되는것을 막아줌
        event.preventDefault();

        console.log('Email', Email);
        console.log('Code', Code);

        let body = {
            email: Email,
            code: Code,
        }
        MailAuthUser(body, setError, history);
    }
    return (
        <div>
            <form onSubmit={onSubmitHandler}>
                <input type="hidden" value={Email}/>
                <input type={"text"} value={Code} onChange={onCodeHandler} placeholder={"코드를 입력해주세요."}/>
                <br/>
                <span>{Error}</span>
                <button formAction={""}>
                    이메일 인증
                </button>
            </form>
            <Link to="/">
                <button>홈으로 가기</button>
            </Link>
        </div>
    )
}

export default MailAuth;