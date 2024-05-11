import './Navbar.css'
import { assets } from '../../assets/assets';
import { useContext, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { StoreContext } from '../../context/StoreContext';


function Navbar({setShowLogin}){
    const navigate = useNavigate();
    const [menu,setMenu] = useState('home');
    const {getTotalCartAmount,token,setToken,setCartItems} = useContext(StoreContext);
    
    function logout(){
        localStorage.removeItem('token');
        setToken('');
        setCartItems({});
        navigate('/');
    }

    return(
        <div className="navbar">
            <Link to='/' onClick={() => setMenu('home')}><img src={assets.logo} className="logo"/></Link>
            <ul className="navbar-menu">
                <Link to='/' onClick={() => setMenu('home')} className={menu === 'home' ? 'active' : ''}>home</Link>
                <li onClick={() => setMenu('menu')} className={menu === 'menu' ? 'active' : ''}>menu</li>
                <li onClick={() => setMenu('mobile-app')} className={menu === 'mobile-app' ? 'active' : ''}>mobile-app</li>
                <li onClick={() => setMenu('contact-us')} className={menu === 'contact-us' ? 'active' : ''}>contact us</li>
            </ul>
            <div className="navbar-right">
                <img src={assets.search_icon}/>
                <div className="navbar-search-icon">
                    <Link to='/cart'><img src={assets.basket_icon}/></Link>
                    <div className={getTotalCartAmount() === 0 ? '' : 'dot'}></div>
                </div>
                {
                    !token
                    ?   <button onClick={() => setShowLogin(true)}>Sign in</button> 
                    :   <div className="navbar-profile">
                            <img src={assets.profile_icon}/>
                            <ul className="nav-profile-dropdown">
                                <li onClick={() => navigate('/myorders')}><img src={assets.bag_icon}/><p>Orders</p></li>
                                <hr/>
                                <li onClick={logout}><img src={assets.logout_icon}/><p>Logout</p></li>
                            </ul>
                        </div>
                }
            </div>
        </div>
    );
}

export default Navbar