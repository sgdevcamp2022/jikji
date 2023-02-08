import {
    BrowserRouter as Router,
    Routes,
    Route
} from "react-router-dom";

import Home from "./home/Home";
import Test from "./test/Test";

function App() {
    return (
        <Router>
            <Routes>
                <Route path={"/"} element={<Home/>}/>
            </Routes>
            <Routes>
                <Route path={"/board/test"} element={<Test/>}/>
            </Routes>
        </Router>
    );
}

export default App;
