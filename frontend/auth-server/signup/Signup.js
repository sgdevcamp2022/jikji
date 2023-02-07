import {useState} from "react";
import {SignupUser} from "../action/SignupUser";
import {Link, useHistory} from "react-router-dom";

function Signup() {
    const [Email, setEmail] = useState("");
    const [Password, setPassword] = useState("");
    const [Name, setName] = useState("");
    const [Nickname, setNickname] = useState("");
    const [Phone, setPhone] = useState("");
    const [EmailError, setEmailError] = useState("");
    const [PasswordError,setPasswordError] = useState("");
    const [NicknameError,setNicknameError] = useState("");
    const [PhoneError, setPhoneError] = useState("");
    const history = useHistory();
    const onEmailHandler = (event) => {
        setEmail(event.currentTarget.value);
    }
    const onPasswordHandler = (event) => {
        setPassword(event.currentTarget.value);
    }

    const onNameHandler = (event) => {
        setName(event.currentTarget.value);
    }

    const onNicknameHandler = (event) => {
        setNickname(event.currentTarget.value);
    }

    const onPhoneHandler = (event) => {
        setPhone(event.currentTarget.value);
    }

    const onSubmitHandler = (event) => {
        // 버튼만 누르면 리로드 되는것을 막아줌
        event.preventDefault();

        console.log('Email', Email);
        console.log('Password', Password);
        console.log("Name",Name);
        console.log("Nickname",Nickname);
        console.log("Phone",Phone);

        let body = {
            email: Email,
            password: Password,
            name: Name,
            nickname: Nickname,
            phone: Phone
        }

        SignupUser(body, setEmailError, setPasswordError, setNicknameError,setPhoneError,history);

    }

    return (
        <div>
            <form onSubmit={onSubmitHandler}>
                <input type="email" value={Email} onChange={onEmailHandler} placeholder={"이메일 주소"}/>
                <span>{EmailError}</span>
                <br/>
                <input type="password" value={Password} onChange={onPasswordHandler} placeholder={"비밀번호"}/>
                <span>{PasswordError}</span>
                <br/>
                <input type="name" value={Name} onChange={onNameHandler} placeholder={"성명"}/>
                <br/>
                <input type="nickname" value={Nickname} onChange={onNicknameHandler} placeholder={"사용자 이름"}/>
                <span>{NicknameError}</span>
                <br/>
                <input type="phone" value={Phone} onChange={onPhoneHandler} placeholder={"휴대폰 번호"}/>
                <span>{PhoneError}</span>
                <br/>
                <button formAction="">
                    회원가입
                </button>
            </form>
            <Link to="/">
                <button>뒤로가기</button>
            </Link>
        </div>
    )
}

export default Signup;