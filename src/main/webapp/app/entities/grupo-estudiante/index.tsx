import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GrupoEstudiante from './grupo-estudiante';
import GrupoEstudianteDetail from './grupo-estudiante-detail';
import GrupoEstudianteUpdate from './grupo-estudiante-update';
import GrupoEstudianteDeleteDialog from './grupo-estudiante-delete-dialog';

const GrupoEstudianteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<GrupoEstudiante />} />
    <Route path="new" element={<GrupoEstudianteUpdate />} />
    <Route path=":id">
      <Route index element={<GrupoEstudianteDetail />} />
      <Route path="edit" element={<GrupoEstudianteUpdate />} />
      <Route path="delete" element={<GrupoEstudianteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GrupoEstudianteRoutes;
