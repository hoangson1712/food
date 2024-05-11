import './ExploreMenu.css'
import { menu_list } from '../../assets/assets';

function ExploreMenu({category,setCategory}){
    return(
        <div className="explore-menu" id='explore-menu'>
            <h1>Explore our menu</h1>
            <p className='explore-menu-text'>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.</p>
            <div className="explore-menu-list">
                {menu_list.map((item,index) => {
                    return(
                        <div onClick={() => setCategory(c => c === item.menu_name ? 'All' : item.menu_name)} key={index} className="explore-menu-list-item">
                            <img className={category === item.menu_name ? 'active' : ''} src={item.menu_image}/>
                            <p>{item.menu_name}</p>
                        </div>
                    );
                })}
            </div>
            <hr/>
        </div>
    );
}

export default ExploreMenu