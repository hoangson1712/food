import { useContext } from 'react';
import './FoodDisplay.css'
import { StoreContext } from '../../context/StoreContext';
import FoodItem from '../FoodItem/FoodItem';

function FoodDisplay({category}){
    const {food_list} = useContext(StoreContext);

    return(
        <div className="food-display" id="food-display">
            <h2>Top dishes near you</h2>
            <div className="food-display-list">
                {food_list.map((item,index) => {
                    if(category === 'All' || category === item.foodCategory){
                        return(
                            <FoodItem key={index} id={item.foodId} name={item.foodName} description={item.foodDesc} price={item.foodPrice} image={item.foodImage}/>
                        );
                    }
                })}
            </div>
        </div>
    );
}

export default FoodDisplay