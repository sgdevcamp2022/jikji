import axios from "axios";
import {useEffect, useState} from "react";
import React from "react";
import {updatePassword} from "../action/updatePassword";

function UpdatePassword() {
    const [Email, setEmail] = useState("");
    const [Password, setPassword] = useState("");
    const [VerifyPassword, setVerifyPassword] = useState("");

    const [EmailError, setEmailError] = useState("");
    const [PasswordError, setPasswordError] = useState("");
    const onEmailHandler = (event) => {
        setEmail(event.currentTarget.value)
    }

    const onPasswordHandler = (event) => {
        setPassword(event.currentTarget.value)
    }

    const onVerifyPasswordHandler = (event) => {
        setVerifyPassword(event.currentTarget.value)
    }

    useEffect(() => {
        axios.get("/accessToken/get-email", {
            headers: {
                'content-type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem("accessToken")
            }
        })
            .then(res => {
                console.log(res.data)
            })
            .catch(error => console.log(error))
    },[])

    const onSubmitHandler =(event)=>{
        event.preventDefault();

        let body = {
            email:Email,
            password:Password,
            verifyPassword:VerifyPassword
        }

        updatePassword(body,setEmailError,setPasswordError);
    }

    return (
        <div>
            <form onSubmit={onSubmitHandler}>
                <input id={"email"} type={"email"} value={Email} onChange={onEmailHandler} placeholder={"이메일"}/>
                <span>{EmailError}</span>
                <br/>
                <input id={"password"} type={"password"} value={Password} onChange={onPasswordHandler} placeholder={"비밀번호"}/>
                <span>{PasswordError}</span>
                <br/>
                <input id={"verifyPassword"} type={"password"} value={VerifyPassword} onChange={onVerifyPasswordHandler} placeholder={"비밀번호 확인"}/>
                <button formAction={""}>비밀번호 변경</button>
            </form>
        </div>
    )
}

export default UpdatePassword;