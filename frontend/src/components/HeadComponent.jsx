import React, { Component } from 'react';


class HeadComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {

        };

        this.redirectMain = this.redirectMain.bind(this);
    }

    redirectMain() {
        window.location.href = '/restaurants';
    }


    render() {
        return (
                    <header>
                        <nav onClick={this.redirectMain} className={ "navbar alert bg-dark" }>
                            <div className="container-fluid">
                                <h1 className={ "display-5 text-light" } >
                                    Menu vote
                                </h1>
                            </div>
                        </nav>
                    </header>
        )
    }
}

export default HeadComponent;