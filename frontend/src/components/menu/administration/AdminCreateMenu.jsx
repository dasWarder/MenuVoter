import React,{ Component } from "react";

class AdminCreateMenu extends Component {

    constructor(props) {
        super(props);

        this.state = {
            menu_id: this.props.match.params.menu_id,
            restaurant_id: this.props.match.params.restaurant_id,
            dishes: []
        }
    }


    render() {
        return(
            <div className={ "row" }>
                <div className={ "card col-md-5 offset-md-4 p-3" }>
                    <h3 className={ "text text-center text-primary mb-2" }>
                        Create menu for today
                    </h3>
                    <div className={ "row" }>
                        <div className={ "card-body col-md-12" }>
                            <form>
                                <div className={ "form-group" }>
                                    <p className={ "lead " }><b>First course</b></p>
                                    <div className={ "mb-3 alert alert-info" }>
                                        <label className={ "mb-1" }><i>Title</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Title" }/>
                                        <label className={ "mb-1" }><i>Description</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Description" }/>
                                    </div>
                                    <p className={ "lead" }><b>Second course</b></p>
                                    <div className={ "mb-3 alert alert-info" }>
                                        <label className={ "mb-1" }><i>Title</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Title" }/>
                                        <label className={ "mb-1" }><i>Description</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Description" }/>
                                    </div>
                                    <p className={ "lead" }><b>Dessert</b></p>
                                    <div className={ "mb-3 alert alert-info" }>
                                        <label className={ "mb-1" }><i>Title</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Title" }/>
                                        <label className={ "mb-1" }><i>Description</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Description" }/>
                                    </div>
                                    <p className={ "lead" }><b>Beverage</b></p>
                                    <div className={ "mb-3 alert alert-info" }>
                                        <label className={ "mb-1" }><i>Title</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Title" }/>
                                        <label className={ "mb-1" }><i>Description</i></label>
                                        <input type={ "text" } className={ "form-control col-md-12" } placeholder={ "Description" }/>
                                    </div>
                                    <button type="submit" className="btn btn-success col-md-3 mx-1">Create</button>
                                    <button type="submit" className="btn btn-danger col-md-3 mx-1">Cancel</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default AdminCreateMenu;
