import React, {useState} from 'react';
import {useEffect} from 'react';
import "./ClubSearch.css";
import ClubSearchBar from "./ClubSearchBar.js";
import ClubTagBox from "./ClubTagBox.js";
import ClubResults from "./ClubResults.js";
import ClubService from './ClubService.js';

const ClubSearchPage = (props) => {
    
    const [clubSearchQuery, setClubSearchQuery] = useState("");
    const [allClubTags, setAllClubTags] = useState([]);
    const [clubTagFilters, setClubTagFilters] = useState([]);
    const [clubResultList, setClubResultList] = useState([]);
    const [allClubs, setAllClubs] = useState([]);
    
        
    useEffect(() => {                                                       
        setClubResultList(allClubs.filter(club => clubSearchQuery.split(" ").every(word => club.name.split(" ").includes(word)) 
    && clubTagFilters.every(tag => club.tags.includes(tag))));
    }, [clubSearchQuery, clubTagFilters]);
    
    
    useEffect(() => {
        ClubService.doClubList().then(response => response.json()).then(json => {
            setAllClubs(json);

        }).catch(err => {console.log("backend is not responding")})}, []);

    useEffect(() => {
        ClubService.doClubTags().then(response => response.json()).then(json => {
            setAllClubTags(json);

        }).catch(err => {console.log("backend is not responding")})}, []);

    
    return (
        <>
        
        <br/><br/><br/><br/><br/>
        <div className="container" id="container">
           
                
                <h1 className="clubSearchPageH1" style={{textAlign: "center"}}>Search for Clubs!</h1>
                 <ClubSearchBar clubSearchQuery = {clubSearchQuery} setClubSearchQuery = {setClubSearchQuery}/>
                <div className="row">
                <ClubTagBox allClubTags = {allClubTags} setAllClubTags = {setAllClubTags} clubTagFilters = {clubTagFilters} setClubTagFilters = {setClubTagFilters}/>
            
                <ClubResults clubResultList = {clubResultList}/>
                </div>
               
       
        </div>
        
      </>
        );
}

export default ClubSearchPage;