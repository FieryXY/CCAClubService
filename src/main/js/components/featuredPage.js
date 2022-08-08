import React, { useEffect, useState } from "react";
import FeaturedClubContent from "./featuredClubContent";
import "./featuredPage.css";
import ClubService from '../ClubService.js';

function FeaturedPage() {
  const [featuredClubs, setFeaturedClubs] = useState(null);
  const [clubIndex, setClubIndex] = useState(0);

  useEffect(() => {

      ClubService.getFeaturedClubs().then(response => {

        if(response.status != 200) {
            throw "Backend is Not Responding!"
        }

        return response.json();
    }).then(json => {
          setFeaturedClubs(json);
          
          //If there are no clubs in featuredClubs, add a placeholder
          if(featuredClubs == null || featuredClubs.length == 0) {
            setFeaturedClubs([
              {
                "clubId": null,
                "clubName": "Club Name",
                "description": "Description",
                "mediaURL": null
              }
            ]);
          }

      }).catch(exc => {
        alert("Request Failed");
      })
  }, []);

  if(featuredClubs == null) {
    return (<h1 style={{color:"white", textAlign:"center"}}>Could not Load Featured Clubs</h1>);
  }



  function clubContentSlideLeft() {
    var elem = document.getElementById("clubContent");
    elem.classList.remove("slide-in-left");
    elem.classList.remove("slide-in-right");
    elem.classList.add("slide-out-left");
    const animated = document.querySelector(".slide-out-left")
    animated.addEventListener("animationend", () => {
      elem.classList.remove("slide-out-left");
      elem.classList.remove("slide-in-left");
      if(clubIndex < featuredClubs.length - 1){
        setClubIndex(clubIndex + 1);
      } else {
        setClubIndex(0);
      }
      elem.classList.add("slide-in-right");
    });
  }
  function clubContentSlideRight() {
    var elem = document.getElementById("clubContent");
    elem.classList.remove("slide-in-left");
    elem.classList.remove("slide-in-right");
    elem.classList.add("slide-out-right");
    const animated = document.querySelector(".slide-out-right")
    animated.addEventListener("animationend", () => {
      elem.classList.remove("slide-out-right");
      elem.classList.remove("slide-in-right");
      if(clubIndex > 0){
        setClubIndex(clubIndex - 1);
      } else {
        setClubIndex(featuredClubs.length - 1);
      }
      elem.classList.add("slide-in-left");
    });
  }
  return (
    <div className="featured-page-body">
      <div className="featured-clubs-container">
        <h1>Featured Clubs</h1>
        <div id="featuredOuterDiv" className="featured-club-outer-div">
          <div id="left-btn" onClick={clubContentSlideRight} className="featured-club-arrow-side-column left">
            <div className="featured-club-arrow left"></div>
          </div>
          <div id="clubContent" className="featured-club-content-container">
            <FeaturedClubContent featuredClubs={featuredClubs} setFeaturedClubs={setFeaturedClubs} clubIndex={clubIndex}/>
          </div>
          <div id="right-btn" onClick={clubContentSlideLeft} className="featured-club-arrow-side-column right">
            <div className="featured-club-arrow right"> </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default FeaturedPage;