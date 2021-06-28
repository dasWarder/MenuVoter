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

    createRestaurant(restaurant) {

        console.log('Create a restaurant with a name=', restaurant.title);

        return axios.post(BASE_RESTAURANT_URL + '/restaurant', restaurant);
    }

    deleteRestaurantById(id) {

        console.log('Remove a restaurant by ID=', id);

        return axios.delete(BASE_RESTAURANT_URL + '/restaurant/' + id);
    }

    updateRestaurant(id, restaurant) {

        console.log('Update a restaurant with ID=', id);

        return axios.put(BASE_RESTAURANT_URL + '/restaurant/' + id, restaurant);
    }

}

export default new RestaurantService();