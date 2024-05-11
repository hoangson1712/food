import { useEffect, useState } from 'react';
import './List.css';
import axios from 'axios';
import { toast } from 'react-toastify';

function List({url}){
    const [list,setList] = useState([]);

    async function fetchList(){
        let response = await axios.get(`${url}/api/food/list`);
        if(response.data.success){
            setList(response.data.data);
        }else{
            toast.error('Error');
        }
    }

    async function removeFood(foodId){
        let response = await axios.post(`${url}/api/food/remove`,{foodId:foodId});
        await fetchList();
        if(response.data.success){
            toast.success(response.data.message);
        }else{
            toast.error(response.data.message);
        }
    }

    useEffect(() => {
        fetchList();
    },[]);

    return(
        <div className="list add flex-col">
            <p>All Foods List</p>
            <div className="list-table">
                <div className="list-table-format title">
                    <b>Image</b>
                    <b>Name</b>
                    <b>Category</b>
                    <b>Price</b>
                    <b>Action</b>
                </div>
                {list.map((item,index) => {
                    return(
                        <div key={index} className="list-table-format">
                            <img src={`${url}/api/file/load/` + item.foodImage}/>
                            <p>{item.foodName}</p>
                            <p>{item.foodCategory}</p>
                            <p>${item.foodPrice}</p>
                            <p onClick={() => removeFood(item.foodId)} className='cursor'>X</p>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}

export default List