import axios from "axios";
import { createContext, useEffect, useState } from "react";

export const StoreContext = createContext(null)

function StoreContextProvider(props){
    const [showLogin,setShowLogin] = useState(false);
    const [token,setToken] = useState('');
    const url = 'http://localhost:8080';
    const [cartItems,setCartItems] = useState({});
    const [food_list,setFoodList] = useState([]);
    const contextValue = {
        food_list,
        cartItems,
        setCartItems,
        addToCart,
        removeFromCart,
        getTotalCartAmount,
        url,
        token,
        setToken,
        showLogin,
        setShowLogin
    };

    async function addToCart(itemId){
        if(!cartItems[itemId]){
            setCartItems((c) => ({...c,[itemId]:1}))
        }else{
            setCartItems((c) => ({...c,[itemId]:c[itemId]+1}))
        }
        if(token){
            await axios.post(url + '/api/cart/add',{'foodId':itemId},{headers:{token}});
        }
    }

    async function removeFromCart(itemId){
        if(cartItems[itemId] === 1){
            const updateCart = {...cartItems};
            delete updateCart[itemId];
            setCartItems(updateCart);
        }else{
            setCartItems((c) => ({...c,[itemId]:c[itemId]-1}))
        }
        if(token){
            await axios.post(url + '/api/cart/remove',{'foodId':itemId},{headers:{token}});
        }
    }

    function getTotalCartAmount(){
        let totalAmount = 0;
        if(food_list.length === 0){
            return totalAmount;
        }
        for(let item in cartItems){
            let itemInfo = food_list.find((product) => product.foodId === Number(item));
            totalAmount += itemInfo.foodPrice * cartItems[item];
        }
        return totalAmount;
    }

    async function fetchFoodList(){
        let response = await axios.get(url + '/api/food/list');
        setFoodList(response.data.data);
    }

    async function loadCartData(token){
        let response = await axios.get(url + '/api/cart/get',{headers:{token}});
        if(response.data.success){
            setCartItems(response.data.cartData);
        }else{
            setCartItems({});
        }
    }

    useEffect(() => {
        async function loadData(){
            await fetchFoodList();
            if(localStorage.getItem('token')){
                setToken(localStorage.getItem('token'));
                await loadCartData(localStorage.getItem('token'));
            }
        }
        loadData();
    },[showLogin]);

    return(
        <StoreContext.Provider value={contextValue}>
            {props.children}
        </StoreContext.Provider>
    );
}

export default StoreContextProvider