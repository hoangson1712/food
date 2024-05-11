import { useState } from 'react';
import './Orders.css'
import axios from 'axios';
import { toast } from 'react-toastify';
import { useEffect } from 'react';
import { assets } from '../../assets/assets';

function Orders({url}){
    const [orders,setOrders] = useState([]);

    async function fetchAllOrders(){
        let response = await axios.get(url + '/api/order/list');
        if(response.data.success){
            setOrders(response.data.data);
            console.log(response.data.data);
        }else{
            toast.error('Error');
        }
    }

    async function statusHandle(e,orderId){
        let response = await axios.put(url + '/api/order/status',{
            orderId,
            orderStatus : e.target.value
        });
        if(response.data.success){
            await fetchAllOrders();
        }
    }

    useEffect(() => {
        fetchAllOrders();
    },[]);

    return(
        <div className="order add">
            <h3>Order Page</h3>
            <div className="order-list">
                {orders.map((order,index) => {
                    return(
                        <div key={index} className="order-item">
                            <img src={assets.parcel_icon}/>
                            <div>
                                <p className="order-item-food">
                                    {order.orderItems.map((item,index) => {
                                        if(index === order.orderItems.length - 1){
                                            return item.foodName + ' x ' + item.foodQuantity;
                                        }else{
                                            return item.foodName + ' x ' + item.foodQuantity + ', ';
                                        }
                                    })}
                                </p>
                                <p className="order-item-name">{order.orderAddress.firstName + ' ' + order.orderAddress.lastName}</p>
                                <div className="order-item-address">
                                    <p>{order.orderAddress.street + ','}</p>
                                    <p>{order.orderAddress.ward + ', ' + order.orderAddress.district + ', ' + order.orderAddress.city + ', ' + order.orderAddress.country}</p>
                                </div>
                                <p className="order-item-phone">{order.orderAddress.phone}</p>
                            </div>
                            <p>Items: {order.orderItems.length}</p>
                            <p>${order.orderAmount}</p>
                            <select onChange={(e) => statusHandle(e,order.orderId)} value={order.orderStatus}>
                                <option value="Food Processing">Food Processing</option>
                                <option value="Out for delivery">Out for delivery</option>
                                <option value="Delivered">Delivered</option>
                            </select>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}

export default Orders