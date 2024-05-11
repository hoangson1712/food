import { useEffect, useState } from 'react';
import './MyOrders.css';
import { useContext } from 'react';
import { StoreContext } from '../../context/StoreContext';
import axios from 'axios';
import { assets } from '../../assets/assets';

function MyOrders(){
    const {url,token} = useContext(StoreContext);
    const [data,setData] = useState([]);

    async function fetchOrders(){
        let response = await axios.get(url + '/api/order/userorder',{headers:{token}});
        if(response.data.success){
            setData(response.data.data);
            console.log(response.data.data);
        }else{
            setData([]);
        }
    }

    useEffect(() => {
        if(token){
            fetchOrders();
        }
    },[token]);

    return(
        <div className="my-orders">
            <h2>My Orders</h2>
            <div className="container">
                {data.map((order,index) => {
                    return(
                        <div key={index} className="my-orders-order">
                            <img src={assets.parcel_icon}/>
                            <p>{order.orderItems.map((item,index) => {
                                if(index === order.orderItems.length-1){
                                    return item.foodName + ' x ' + item.foodQuantity;
                                }else{
                                    return item.foodName + ' x ' + item.foodQuantity + ', ';
                                }
                            })}</p>
                            <p>${order.orderAmount}</p>
                            <p>Items: {order.orderItems.length}</p>
                            <p><span>&#x25cf;</span> <b>{order.orderStatus}</b></p>
                            <button onClick={fetchOrders}>Track Order</button>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}

export default MyOrders