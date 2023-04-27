import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SemestreAcademico from './semestre-academico';
import SemestreAcademicoDetail from './semestre-academico-detail';
import SemestreAcademicoUpdate from './semestre-academico-update';
import SemestreAcademicoDeleteDialog from './semestre-academico-delete-dialog';

const SemestreAcademicoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SemestreAcademico />} />
    <Route path="new" element={<SemestreAcademicoUpdate />} />
    <Route path=":id">
      <Route index element={<SemestreAcademicoDetail />} />
      <Route path="edit" element={<SemestreAcademicoUpdate />} />
      <Route path="delete" element={<SemestreAcademicoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SemestreAcademicoRoutes;
