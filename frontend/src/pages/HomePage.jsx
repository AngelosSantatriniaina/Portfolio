import {useProfile} from '../hooks/usePortfolio';
import {HeroSection} from '../components/portfolio/HeroSection';
import {AboutSection} from '../components/portfolio/AboutSection';
import {SkillsSection} from '../components/portfolio/SkillsSection';
import {ExperienceSection} from '../components/portfolio/ExperienceSection';
import {ProjectsSection} from '../components/portfolio/ProjectsSection';
import {ServicesSection} from '../components/portfolio/ServicesSection';
import {ContactSection} from '../components/portfolio/ContactSection';
import {GallerySection} from '../components/portfolio/GallerySection';
import {CvSection} from '../components/portfolio/CvSection';
export function HomePage(){const q=useProfile();return <><HeroSection profile={q.data}/><AboutSection profile={q.data}/><ExperienceSection/><SkillsSection/><ServicesSection/><ProjectsSection/><GallerySection profile={q.data}/><CvSection profile={q.data}/><ContactSection/></>;}
