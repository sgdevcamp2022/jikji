import React, {useState} from "react";
import {loginUser} from "../action/loginUser";
import {Link,useHistory} from "react-router-dom";

function Home() {

    const [Email, setEmail] = useState("");
    const [Password, setPassword] = useState("");
    const [EmailError, setEmailError] = useState("");
    const [PasswordError, setPasswordError] = useState("");

    const history = useHistory();
    const onEmailHandler = (event) => {
        setEmail(event.currentTarget.value);
    }
    const onPasswordHandler = (event) => {
        setPassword(event.currentTarget.value);
    }
    const onSubmitHandler = (event) => {
        // 버튼만 누르면 리로드 되는것을 막아줌
        event.preventDefault();

        console.log('Email', Email);
        console.log('Password', Password);

        let body = {
            email: Email,
            password: Password,
        }
        loginUser(body,setEmailError, setPasswordError, history);
    }
    return (
        <div>
            <form onSubmit={onSubmitHandler}>
                <input type="email" value={Email} onChange={onEmailHandler} placeholder={"이메일"}/>
                <span>{EmailError}</span>
                <br/>
                <input type="password" value={Password} onChange={onPasswordHandler} placeholder={"비밀번호"}/>
                <span>{PasswordError}</span>
                <br/>
                <button formAction="">
                    로그인
                </button>
            </form>
            <Link to="/signup">
                <button>회원가입</button>
            </Link>
            <Link to={"/searchPassword"}>
                <button>비밀번호 찾기</button>
            </Link>
        </div>
    )
}

export default Home;