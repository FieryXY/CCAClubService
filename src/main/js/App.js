import React, {useState, useEffect} from 'react';
import { render } from 'react-dom';
import './App.css';
import Nav from './Nav';
import About from './About';
import Shop from './Shop';
import Intro from './Intro';
import Login from './login';
import ClubProfile from './ClubProfile';
import ClubSearchPage from './ClubSearch';
import ClubEditor from './ClubEditor';
import { HashRouter as Router, Switch, Route, Routes } from 'react-router-dom';
import {Navigate} from "react-router-dom";
import FeaturedPage from "./components/featuredPage";
import Modal from 'react-modal';
import {useNavigate} from "react-router-dom";

Modal.setAppElement("#root"); 
function App() { 
  
  const [isLoggedIn, setLoggedIn] = useState(false);
  const logOut = (navigate) => {
    if(sessionStorage.getItem("accessToken") != null) {
      sessionStorage.removeItem("accessToken");
      sessionStorage.removeItem("clubId");
        setLoggedIn(false);

        let currentURL = window.location.href;
        if(currentURL.endsWith("#/club-editor")) {
          navigate("/login")
        }
    }
  }

  useEffect(() => {
    if(!isLoggedIn && sessionStorage.getItem("accessToken") != null) {
      setLoggedIn(true)
    }
  }, []);

  return (
    <Router>
      <div className="App">
        <Nav logOut={logOut} isLoggedIn = {isLoggedIn} setLoggedIn = {setLoggedIn}/>
          <Routes>
              <Route exact path="/" element={<Navigate to="/about"/>}/>
              <Route path="/featured-page" element={<FeaturedPage />} />
              <Route path="/about" element={<Intro />} />
              <Route path="/manage-clubs" element={<About />} />
              <Route path="/asb" element={<About />} />
              <Route path="/club-search" element={<ClubSearchPage />} />
              <Route path="/club-profile/:clubId" element={<ClubProfile />} />
              <Route path="/club-editor" element={<ClubEditor />} />
              if(!isLoggedIn) {
                <Route path="/login" element={<Login isLoggedIn={isLoggedIn} setLoggedIn={setLoggedIn}/>} />
              }
              if(isLoggedIn) {
                <Route path="/editor" element={<About />} />
              }
          </Routes>
      </div>
    </Router>
  );

}

export default App;
