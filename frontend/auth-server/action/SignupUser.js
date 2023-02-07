import axios from "axios";

export async function SignupUser(data, setEmailError, setPasswordError, setNicknameError, setPhoneError,history) {
    await axios.post('/signup/register', data)
        .then(response => {
                console.log(response.data)

                setEmailError("");
                setPhoneError("");
                setPasswordError("");
                setNicknameError("");

                switch (response.data) {
                    case "ExistsUserEmail":
                        setEmailError("사용중인 이메일입니다.");
                        break;
                    case "WrongEmailFormat":
                        setEmailError("이메일 형식이 올바르지 않습니다.");
                        break;
                    case "WrongPasswordFormat":
                        setPasswordError("비밀번호 형식이 올바르지 않습니다.");
                        break;
                    case "ExistsUserNickname":
                        setNicknameError("사용중인 닉네임입니다.");
                        break;
                    case "WrongNicknameFormat":
                        setNicknameError("닉네임 형식이 올바르지 않습니다.");
                        break;
                    case "WrongPhoneFormat":
                        setPhoneError("전화번호 형식이 올바르지 않습니다.");
                        break;
                    case "SUCCESS":
                        alert("등록하신 이메일로 인증코드를 보냈습니다.");
                        history.push("/signup/mailAuth", data.email);
                        break;
                }

            }
        )
        .catch(error => console.log(error.message));
}