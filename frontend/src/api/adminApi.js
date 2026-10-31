import {http} from './http';

const resource = path => ({
  list: async () => (await http.get(`/admin/${path}`)).data,
  create: async payload => (await http.post(`/admin/${path}`, payload)).data,
  update: async (id, payload) => (await http.put(`/admin/${path}/${id}`, payload)).data,
  remove: async id => http.delete(`/admin/${path}/${id}`),
});

export const adminApi = {
  getProfile: async () => (await http.get('/admin/profile')).data,
  updateProfile: async payload => (await http.put('/admin/profile', payload)).data,
  uploadImage: async file => {
    const formData = new FormData();
    formData.append('file', file);
    return (await http.post('/admin/uploads', formData, {
      headers: {'Content-Type': undefined},
    })).data;
  },
  aboutItems: resource('about-items'),
  experiences: resource('experiences'),
  skills: resource('skills'),
  skillCategories: resource('skill-categories'),
  services: resource('services'),
  projects: resource('projects'),
  contactLinks: resource('contact-links'),
  reviews: resource('reviews'),
  getMessages: async () => (await http.get('/admin/messages')).data,
  markMessageRead: async id => (await http.patch(`/admin/messages/${id}/read`)).data,
  deleteMessage: async id => http.delete(`/admin/messages/${id}`),
};
