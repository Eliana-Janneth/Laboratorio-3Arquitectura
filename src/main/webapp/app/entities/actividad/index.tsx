import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Actividad from './actividad';
import ActividadDetail from './actividad-detail';
import ActividadUpdate from './actividad-update';
import ActividadDeleteDialog from './actividad-delete-dialog';

const ActividadRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Actividad />} />
    <Route path="new" element={<ActividadUpdate />} />
    <Route path=":id">
      <Route index element={<ActividadDetail />} />
      <Route path="edit" element={<ActividadUpdate />} />
      <Route path="delete" element={<ActividadDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ActividadRoutes;
