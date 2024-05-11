import { Route, Routes } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import Home from "./pages/Home/Home";
import Cart from "./pages/Cart/Cart";
import PlaceOrder from "./pages/PlaceOrder/PlaceOrder";
import Footer from "./components/Footer/Footer";
import { useContext } from "react";
import LoginPopup from "./components/LoginPopup/LoginPopup";
import { StoreContext } from "./context/StoreContext";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import MyOrders from "./pages/MyOrders/MyOrders";

function App(){
    const {showLogin,setShowLogin} = useContext(StoreContext);

    return(
        <>
            {showLogin ? <LoginPopup setShowLogin={setShowLogin}/> : <></>}
            <div className="app">
                <ToastContainer/>
                <Navbar setShowLogin={setShowLogin}/>
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/cart" element={<Cart/>}/>
                    <Route path="/order" element={<PlaceOrder/>}/>
                    <Route path="/myorders" element={<MyOrders/>}/>
                </Routes>
            </div>
            <Footer/>
        </>
        
    );
}

export default App