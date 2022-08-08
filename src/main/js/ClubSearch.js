import React, {useState} from 'react';
import {useEffect} from 'react';
import "./ClubSearch.css";
import ClubSearchBar from "./ClubSearchBar.js";
import ClubTagBox from "./ClubTagBox.js";
import ClubResults from "./ClubResults.js";
import ClubService from './ClubService.js';

const ClubSearchPage = () => {
    
    const [clubSearchQuery, setClubSearchQuery] = useState("");
    const [allClubTags, setAllClubTags] = useState([]);
    const [clubTagFilters, setClubTagFilters] = useState([]);
    const [clubResultList, setClubResultList] = useState([]);
    const [allClubs, setAllClubs] = useState([]);

    const filterClubs = () => {
        let filteredClubs = allClubs;
        if(clubTagFilters.length > 0) {
            filteredClubs = filteredClubs.filter(club => {
                return clubTagFilters.every(tag => club["clubCategories"].includes(tag));
            });
        }
        if(clubSearchQuery !== "") {
            filteredClubs = filteredClubs.filter(club => {
                return club["clubName"].toLowerCase().includes(clubSearchQuery.toLowerCase());
            });
        }
        setClubResultList(filteredClubs);
    }    
    
    useEffect(() => {
        ClubService.doClubList().then(response => {
            if(response["status"] != 200) {
                throw "Backend is not responding"
            }
            return response.json();
        }).then(json => {
            setAllClubs(json);
            filterClubs();
        }).catch(err => {console.log("backend is not responding")})    
    }, []);

    useEffect(() => {
        ClubService.doClubTags().then(response => {
            if(response["status"] != 200) {
                throw "Backend is not responding"
            }
            return response.json();
        }).then(json => {
            setAllClubTags(json);
        }).catch(err => {console.log("backend is not responding")})}, []);

        useEffect(() => {
            filterClubs();
        }, [allClubs]);

        useEffect(() => {
            filterClubs();
        }, [clubSearchQuery, clubTagFilters]);


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