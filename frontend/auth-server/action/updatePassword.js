import axios from "axios";

export async function updatePassword(data,setEmailError, setPasswordError){
    await axios.post("/user/update-password",data)
        .then(res=>{
            console.log(res.data)
            new Notification("비밀번호변경",{body:res.data})
        })
}