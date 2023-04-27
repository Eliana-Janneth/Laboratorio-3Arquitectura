import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AuditoriaNota from './auditoria-nota';
import AuditoriaNotaDetail from './auditoria-nota-detail';
import AuditoriaNotaUpdate from './auditoria-nota-update';
import AuditoriaNotaDeleteDialog from './auditoria-nota-delete-dialog';

const AuditoriaNotaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AuditoriaNota />} />
    <Route path="new" element={<AuditoriaNotaUpdate />} />
    <Route path=":id">
      <Route index element={<AuditoriaNotaDetail />} />
      <Route path="edit" element={<AuditoriaNotaUpdate />} />
      <Route path="delete" element={<AuditoriaNotaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AuditoriaNotaRoutes;
