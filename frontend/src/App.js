import './App.css';
import HeadComponent from "./components/HeadComponent";
import FooterComponent from "./components/FooterComponent";
import RestaurantsListComponent from "./components/restaurant/RestaurantsListComponent";
import TodayMenuComponent from "./components/menu/TodayMenuComponent";
import VoteComponent from "./components/vote/VoteComponent";
import AdminRestaurantListComponent from "./components/restaurant/administration/AdminRestaurantListComponent";
import CreateRestaurantComponent from "./components/restaurant/administration/CreateRestaurantComponent";
import AdminMenuListComponent from "./components/menu/administration/AdminMenuListComponent";
import AllMenusListComponent from "./components/menu/administration/AllMenusListComponent";
import { BrowserRouter as Router, Route, Switch} from 'react-router-dom';

function App() {
  return (
      <div>
          <div className={ "container-fluid" } >
              <Router>
                  <HeadComponent/>
                  <div className={ "row my-5" }>
                      <div className={ "col-md-8 offset-md-2" }>

                          <Switch>
                            <Route path={ "/" } exact={ true } component={ RestaurantsListComponent }></Route>
                            <Route path={ "/restaurants" } exact={ true } component={ RestaurantsListComponent }></Route>
                            <Route path={ "/admin/restaurants" } exact={ true } component={ AdminRestaurantListComponent }></Route>
                            <Route path={ "/admin/restaurants/restaurant/:id" } exact={ true } component={ CreateRestaurantComponent }></Route>
                            <Route path={ "/admin/restaurants/restaurant/:restaurant_id/menu" } exact={ true } component={ AdminMenuListComponent }></Route>
                            <Route path={ "/admin/restaurants/restaurant/:restaurant_id/menu/menus" } exact={ true } component={ AllMenusListComponent }></Route>
                            <Route path={ "/restaurants/restaurant/:id/menus/menu" } exact={ true } component={ TodayMenuComponent }></Route>
                            <Route path={ "/restaurants/restaurant/:restaurant_id/menus/menu/:menu_id/rate" } exact={ true } component={ VoteComponent }></Route>
                            <RestaurantsListComponent/>
                          </Switch>
                      </div>
                  </div>
                  <FooterComponent/>
              </Router>
          </div>
      </div>
  );
}

export default App;
