import axios from "axios";

export async function loginUser(data, setEmailError, setPasswordError, history) {
    await axios.post('/user/login', data)
        .then(response => {
                console.log(response.data)
                setEmailError("")
                setPasswordError("")
                if (response.data === "NotFoundUser") {
                    setEmailError("사용자를 찾을 수 없습니다.");
                } else if (response.data === "WrongPassword") {
                    setPasswordError("비밀번호가 일치하지 않습니다.");
                } else if (response.data === "NotEmailAuthUser") {
                    history.push("/signup/mailAuth", data.email);
                }
                const accessToken = response.data["accessToken"];
                const refreshToken = response.data["refreshToken"];
                localStorage.setItem("accessToken", accessToken);
                localStorage.setItem("refreshToken", refreshToken);


                history.push("/board/test",data.email);
            }
        )
        .catch(error => console.log(error));
}