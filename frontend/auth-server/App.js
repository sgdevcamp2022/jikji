import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";
import Home from "./home/Home";
import Signup from "./signup/Signup";
import MailAuth from "./signup/MailAuth";
import AuthUser from "./search/AuthUser";
import Test from "./board/Test";
import UpdatePassword from "./signIn/UpdatePassword";

function App() {
    return (
        <Router>
            <Switch>
                <Route exact path={"/"}>
                    <Home/>
                </Route>
                <Route exact path={"/signup"}>
                    <Signup/>
                </Route>
                <Route exact path={"/signup/mailAuth"}>
                    <MailAuth/>
                </Route>
                <Route exact path={"/searchPassword"}>
                    <AuthUser/>
                </Route>
                <Route exact path={"/board/test"}>
                    <Test/>
                </Route>
                <Route exact path={"/updatePassword"}>
                    <UpdatePassword/>
                </Route>
            </Switch>
        </Router>
    );
}

export default App;
