import React, {useState} from 'react';
import './App.css';
import Nav from './Nav';
import About from './About';
import Shop from './Shop';
import Intro from './Intro';
import Login from './login';
import { MemoryRouter as Router, Switch, Route, Routes } from 'react-router-dom';
import {Navigate} from "react-router-dom";
import FeaturedPage from "./components/featuredPage";

function App() { 

  const [isLoggedIn, setLoggedIn] = useState(false);
  
  return (
    <Router>
      <div className="App">
        <Nav />
          <Routes>
              <Route exact path="/" element={<Navigate to="/about"/>}/>
              <Route path="/featured-page" element={<FeaturedPage />} />
              <Route path="/about" element={<Intro />} />
              <Route path="/manage-clubs" element={<About />} />
              <Route path="/asb" element={<About />} />
              <Route path="/login" element={<Login isLoggedIn={isLoggedIn} setLoggedIn={setLoggedIn}/>} />
          </Routes>
      </div>
    </Router>
  );
}

export default App;
