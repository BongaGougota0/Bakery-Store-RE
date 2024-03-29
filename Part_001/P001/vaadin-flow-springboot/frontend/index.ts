import {Flow} from 'Frontend/generated/jar-resources/Flow';
import {Router} from '@vaadin/router';

const {serverSideRoutes} = new Flow({
  imports: () => import('Frontend/generated/flow/generated-flow-imports.js')
});

const routes = [
  // fallback to server-side Flow routes if no client-side routes match
  ...serverSideRoutes
];

const router = new Router(document.querySelector('#outlet'));
router.setRoutes(routes);
