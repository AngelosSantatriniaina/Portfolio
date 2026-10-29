import {useProfile} from '../hooks/usePortfolio';
import {HeroSection} from '../components/portfolio/HeroSection';
import {AboutSection} from '../components/portfolio/AboutSection';
import {SkillsSection} from '../components/portfolio/SkillsSection';
import {ExperienceSection} from '../components/portfolio/ExperienceSection';
import {ProjectsSection} from '../components/portfolio/ProjectsSection';
import {ServicesSection} from '../components/portfolio/ServicesSection';
import {ReviewsSection} from '../components/portfolio/ReviewsSection';
import {ContactSection} from '../components/portfolio/ContactSection';
export function HomePage(){const q=useProfile();return <><HeroSection profile={q.data}/><AboutSection profile={q.data}/><SkillsSection/><ExperienceSection/><ProjectsSection/><ServicesSection/><ReviewsSection/><ContactSection/></>;}
