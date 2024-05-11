import { useState } from 'react';
import { assets } from '../../assets/assets';
import './Add.css';
import { useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';

function Add({url}){
    const [image,setImage] = useState(false);
    const [data,setData] = useState({
        foodName : '',
        foodDesc : '',
        foodPrice : '',
        foodImage : '',
        foodCategory : 'Salad'
    });

    function onChangeHandle(e){
        let name = e.target.name;
        let value = e.target.value;
        setData(d => ({...d,[name]:value}));
    }

    async function onSubmitHandle(e){
        e.preventDefault();
        let formData = new FormData();
        formData.append('file',image);
        let uploadFile = await axios.post(`${url}/api/file/upload`,formData);
        if(uploadFile.data !== 'Upload Fail'){
            let addFood = await axios.post(`${url}/api/food/add`,data);
            if(addFood.data.success){
                setData({
                    foodName : '',
                    foodDesc : '',
                    foodPrice : '',
                    foodImage : '',
                    foodCategory : 'Salad'
                });
                setImage(false);
                toast.success(addFood.data.message);
            }else{
                toast.error(addFood.data.message);
            }
        }else{
            toast.error(uploadFile.data);
        }
    }

    return(
        <div className="add">
            <form className="flex-col" onSubmit={onSubmitHandle}>
                <div className="add-img-upload flex-col">
                    <p>Upload Image</p>
                    <label htmlFor="image">
                        <img src={image ? URL.createObjectURL(image) : assets.upload_area}/>
                    </label>
                    <input onChange={(e) => {setImage(e.target.files[0]);setData(d => ({...d,foodImage:e.target.files[0].name}));}} type="file" id="image" hidden required/>
                </div>
                <div className="add-food-name flex-col">
                    <p>Food Name</p>
                    <input onChange={onChangeHandle} value={data.foodName} type="text" name="foodName" placeholder='Type here'/>
                </div>
                <div className="add-food-desc flex-col">
                    <p>Food Description</p>
                    <textarea onChange={onChangeHandle} value={data.foodDesc} name="foodDesc" rows='6' placeholder='Write content here' required></textarea>
                </div>
                <div className="add-category-price">
                    <div className="add-category flex-col">
                        <p>Food Category</p>
                        <select onChange={onChangeHandle} value={data.foodCategory} name="foodCategory">
                            <option value="Salad">Salad</option>
                            <option value="Rolls">Rolls</option>
                            <option value="Deserts">Deserts</option>
                            <option value="Sandwich">Sandwich</option>
                            <option value="Cake">Cake</option>
                            <option value="Pure Veg">Pure Veg</option>
                            <option value="Pasta">Pasta</option>
                            <option value="Noodles">Noodles</option>
                        </select>
                    </div>
                    <div className="add-price flex-col">
                        <p>Food Price</p>
                        <input onChange={onChangeHandle} value={data.foodPrice} type="number" name="foodPrice" placeholder='$20'/>
                    </div>
                </div>
                <button type='submit' className='add-btn'>ADD</button>
            </form>
        </div>
    );
}

export default Add