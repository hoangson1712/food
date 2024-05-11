import { useContext, useEffect, useState } from 'react';
import './PlaceOrder.css'
import { StoreContext } from '../../context/StoreContext';
import axios from 'axios';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

function PlaceOrder(){
    const navigate = useNavigate();
    const {getTotalCartAmount,token,food_list,cartItems,url,setCartItems} = useContext(StoreContext);
    const [data,setData] = useState({
        firstName : '',
        lastName : '',
        email : '',
        street : '',
        ward : '',
        district : '',
        city : '',
        country : '',
        phone : ''
    });

    function onChangeHandle(e){
        let name = e.target.name;
        let value = e.target.value;
        setData(d => ({...d,[name]:value}));
    }

    async function placeOrder(e){
        e.preventDefault();
        let orderItems = [];
        food_list.map((item) => {
            if(cartItems[item.foodId] > 0){
                let itemInfo = item;
                itemInfo['foodQuantity'] = cartItems[item.foodId];
                orderItems.push(itemInfo);
            }
        });
        let orderData = {
            orderAddress : data,
            orderItems : orderItems,
            orderAmount : getTotalCartAmount() + 2
        };
        let response = await axios.post(url + '/api/order/place',orderData,{headers:{token}});
        if(response.data.success){
            let deleteCart = await axios.delete(url + '/api/cart/delete',{headers:{token}});
            if(deleteCart.data.success){
                toast.success(response.data.message);
                setCartItems({});
                navigate('/');
            }else{
                toast.error(deleteCart.data.message);
            }
        }else{
            toast.error(response.data.message);
        }
    }

    useEffect(() => {
        if(!token){
            navigate('/cart');
        }else if(getTotalCartAmount() === 0){
            navigate('/cart');
        }
    },[token]);

    return(
        <form onSubmit={placeOrder} className="place-order">
            <div className="place-order-left">
                <p className="title">Delivery Information</p>
                <div className="multi-fields">
                    <input required name='firstName' onChange={onChangeHandle} value={data.firstName} type="text" placeholder='First Name'/>
                    <input required name='lastName' onChange={onChangeHandle} value={data.lastName} type="text" placeholder='Last Name'/>
                </div>
                <input required name='email' onChange={onChangeHandle} value={data.email} type="email" placeholder='Email Address'/>
                <input required name='street' onChange={onChangeHandle} value={data.street} type="text" placeholder='Street'/>
                <div className="multi-fields">
                    <input required name='ward' onChange={onChangeHandle} value={data.ward} type="text" placeholder='Ward'/>
                    <input required name='district' onChange={onChangeHandle} value={data.district} type="text" placeholder='District'/>
                </div>
                <div className="multi-fields">
                    <input required name='city' onChange={onChangeHandle} value={data.city} type="text" placeholder='City'/>
                    <input required name='country' onChange={onChangeHandle} value={data.country} type="text" placeholder='Country'/>
                </div>
                <input required name='phone' onChange={onChangeHandle} value={data.phone} type="text" placeholder='Phone'/>
            </div>
            <div className="place-order-right">
                <div className="cart-total">
                    <h2>Cart Totals</h2>
                    <div>
                        <div className="cart-total-details">
                            <p>Subtotal</p>
                            <p>${getTotalCartAmount()}</p>
                        </div>
                        <hr/>
                        <div className="cart-total-details">
                            <p>Delivery Fee</p>
                            <p>${2}</p>
                        </div>
                        <hr/>
                        <div className="cart-total-details">
                            <b>Total</b>
                            <b>${getTotalCartAmount()+2}</b>
                        </div>
                    </div>
                    <button type='submit'>PROCEED TO PAYMENT</button>
                </div>
            </div>
        </form>
    );
}

export default PlaceOrder