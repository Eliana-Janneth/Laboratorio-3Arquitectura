import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Nota from './nota';
import NotaDetail from './nota-detail';
import NotaUpdate from './nota-update';
import NotaDeleteDialog from './nota-delete-dialog';

const NotaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Nota />} />
    <Route path="new" element={<NotaUpdate />} />
    <Route path=":id">
      <Route index element={<NotaDetail />} />
      <Route path="edit" element={<NotaUpdate />} />
      <Route path="delete" element={<NotaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NotaRoutes;
