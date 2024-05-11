import { useContext, useEffect, useState } from 'react';
import './LoginPopup.css'
import { assets } from '../../assets/assets';
import { StoreContext } from '../../context/StoreContext';
import axios from 'axios';

function LoginPopup({setShowLogin}){
    const {url,setToken} = useContext(StoreContext);
    const [currState,setCurrState] = useState('Login');
    const [data,setData] = useState({
        userName : '',
        userEmail : '',
        userPassword : ''
    });

    function onChangeHandle(e){
        let name = e.target.name;
        let value = e.target.value;
        setData(d => ({...d,[name]:value}));
    }

    async function onLogin(e){
        e.preventDefault();
        let newUrl = url;
        if(currState === 'Login'){
            newUrl += '/api/user/login';
        }else{
            newUrl += '/api/user/register';
        }
        let response = await axios.post(newUrl,data);
        if(response.data.success){
            setToken(response.data.token);
            localStorage.setItem('token',response.data.token);
            setShowLogin(false);
        }else{
            alert(response.data.message);
        }
    }

    return(
        <div className="login-popup">
            <form onSubmit={onLogin} className="login-popup-container">
                <div className="login-popup-title">
                    <h2>{currState}</h2>
                    <img onClick={() => setShowLogin(false)} src={assets.cross_icon}/>
                </div>
                <div className="login-popup-inputs">
                    {currState === 'Login' ? <></> : <input name='userName' onChange={onChangeHandle} value={data.userName} type="text" placeholder='Your name' required/>}
                    <input name='userEmail' onChange={onChangeHandle} value={data.userEmail} type="email" placeholder='Your email' required/>
                    <input name='userPassword' onChange={onChangeHandle} value={data.userPassword} type="password" placeholder='Password' required/>
                </div>
                <button type='submit'>{currState === 'Sign Up' ? 'Create Account' : 'Login'}</button>
                <div className="login-popup-condition">
                    <input type="checkbox" required/>
                    <p>By continuing, i agree to the term of use & privacy policy</p>
                </div>
                {   
                    currState === 'Login'
                    ?<p>Create a new account? <span onClick={() => setCurrState('Sign Up')}>Click Here</span></p>
                    :<p>Already have an account? <span onClick={() => setCurrState('Login')}>Login Here</span></p>
                }
            </form>
        </div>
    );
}

export default LoginPopup