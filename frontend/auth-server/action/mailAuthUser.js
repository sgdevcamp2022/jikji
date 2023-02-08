import axios from "axios";

export async function MailAuthUser(data, setError, history) {
    await axios.post('/signup/mailAuth', data)
        .then(response => {
                console.log(response.data);
                setError("");
                if (response.data === "NotExistsEmail" || response.data === "WrongCode") {
                    setError(response.data);
                } else {
                    alert("이메일 인증이 성공하였습니다.");
                    history.push("/");
                }
            }
        )
        .catch(error => console.log(error));
}