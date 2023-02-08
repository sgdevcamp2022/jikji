import axios from "axios";

export const useNotification = (title, options) => {
    if (!("Notification" in window)) {
        return;
    }

    const fireNotIf = () => {
        if (Notification.permission !== "granted") {
            Notification.requestPermission()
                .then((permission) => {
                    if (permission === "granted") {
                        new Notification(title, options);
                    } else {
                        return;
                    }
                })
        } else {
            axios.get("/user/test")
                .then(res => new Notification(title, {body:res.data}));
        }
    }
    return fireNotIf;
}