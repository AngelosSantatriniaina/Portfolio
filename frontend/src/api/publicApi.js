import {http} from './http';
export const publicApi={
 getProfile:async()=>(await http.get('/public/profile')).data,
 getAboutItems:async()=>(await http.get('/public/about-items')).data,
 getExperiences:async()=>(await http.get('/public/experiences')).data,
 getSkills:async()=>(await http.get('/public/skills')).data,
 getServices:async()=>(await http.get('/public/services')).data,
 getProjects:async()=>(await http.get('/public/projects')).data,
 getReviews:async()=>(await http.get('/public/reviews')).data,
 getContactLinks:async()=>(await http.get('/public/contact-links')).data,
 sendContact:async payload=>(await http.post('/contact',payload)).data,
 addReview:async payload=>(await http.post('/public/reviews',payload)).data,
 addReaction:async(id,payload)=>(await http.post(`/public/projects/${id}/reactions`,payload)).data
};
