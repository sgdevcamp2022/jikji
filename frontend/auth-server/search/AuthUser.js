import axios from "axios";
import React, {useState} from "react";
import {authUser} from "../action/authUser";
import {useHistory} from "react-router-dom";

function AuthUser(){
    const [Email, setEmail] = useState("");
    const [Nickname, setNickname] = useState("");
    const [Phone, setPhone] = useState("");
    const [EmailError, setEmailError] = useState("");
    const [NicknameError, setNicknameError] = useState("");
    const [PhoneError, setPhoneError] = useState("");

    const history = useHistory();
    const onEmailHandler=(event)=>{
        setEmail(event.currentTarget.value);
    }

    const onNicknameHandler=(event)=>{
        setNickname(event.currentTarget.value);
    }

    const onPhoneHandler=(event)=>{
        setPhone(event.currentTarget.value);
    }

    const onSubmitHandler =(event)=>{
        event.preventDefault();

        let body = {
            email:Email,
            nickname:Nickname,
            phone:Phone
        }

        authUser(body,setEmailError,setNicknameError,setPhoneError,history);
    }
     return (
         <div>
             <form onSubmit={onSubmitHandler}>
                 <input id={"email"} type={"email"} value={Email} onChange={onEmailHandler} placeholder={"이메일"}/>
                 <span>{EmailError}</span>
                 <br/>
                 <input id={"nickname"} type={"text"} value={Nickname} onChange={onNicknameHandler} placeholder={"닉네임"}/>
                 <span>{NicknameError}</span>
                 <br/>
                 <input id={"phone"} type={"text"} value={Phone} onChange={onPhoneHandler} placeholder={"전화번호"}/>
                 <span>{PhoneError}</span>
                 <button formAction={""}>비밀번호 찾기</button>
             </form>
         </div>
     )
}

export default AuthUser;