import React,{useState} from "react";
import {useNavigate} from "react-router-dom";

function Home() {
    let navigate = useNavigate();
    const [Email, setEmail] = useState("");
    const onEmailHandler = (event) => {
        setEmail(event.currentTarget.value);
    }

    const onSubmitHandler = (event) => {
        // 버튼만 누르면 리로드 되는것을 막아줌
        event.preventDefault();

        localStorage.setItem("email",Email);
        navigate("board/Test")
    }


    return (
        <div>
            <form onSubmit={onSubmitHandler}>
                <input type="text" value={Email} onChange={onEmailHandler} placeholder={"이메일"}/>
                <br/>
                <button formAction="">
                    로그인
                </button>
            </form>
        </div>
    )
}

export default Home;