import axios from 'axios';


const BASE_RESTAURANT_URL = 'http://localhost:8080/restaurants';

class RestaurantService {

    getAllRestaurants() {

        console.log('Receive a list of all restaurants');

        return axios.get(BASE_RESTAURANT_URL);
    }

    getRestaurantById(id) {

        console.log('Receive a restaurant by ID=', id);

        return axios.get(BASE_RESTAURANT_URL + '/restaurant/' + id);
    }

}

export default new RestaurantService();