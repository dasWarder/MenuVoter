import React, { Component } from 'react';


class FooterComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {

        };
    }


    render() {
        return (
            <footer className={ "container text-center text-md-start mt-5" }>
                <div className={ "container text-center text-md-start mt-5" }>
                    <hr/>
                    <p className={ "text-center" }>
                        Andrey Babichev, 2021
                    </p>
                </div>
            </footer>
        )
    }
}

export default FooterComponent;